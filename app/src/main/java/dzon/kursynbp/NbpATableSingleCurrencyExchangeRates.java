package dzon.kursynbp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class NbpATableSingleCurrencyExchangeRates implements SingleCurrencyExchangeRates {
    private final int maxDays = 90; //API allows maximum 93 days in single request
    private final String jsonRatesField = "rates";
    private final String jsonExchangeRateField = "mid";
    private final String jsonDateField = "effectiveDate";

    @Override
    public SortedSet<CurrencyRate> getExchangeRates(Currency currency, Date start, Date end) throws IOException {
        return getExchangeRates(currency.getCode(), start, end);
    }

    @Override
    public SortedSet<CurrencyRate> getExchangeRates(String currencyCode, Date start, Date end) throws IOException {
        if(daysBetween(start, end) < 0) {
            throw new IOException("End date cannot be earlier than start date");
        }

        SortedSet<CurrencyRate> currencyRates = new TreeSet<>(getComparator());

        try {
            Date currentStart = new Date(start.getTime());
            while (daysBetween(currentStart, end) >= 0) {
                if (daysBetween(currentStart, end) > maxDays) {
                    Date currentEnd = getDateAfterNumberOfDays(currentStart, maxDays);
                    currencyRates.addAll(getCurrencyRates(currencyCode, currentStart, currentEnd));
                    currentStart = getDateAfterNumberOfDays(currentEnd, 1);
                } else {
                    currencyRates.addAll(getCurrencyRates(currencyCode, currentStart, end));
                }
            }
        } catch(ParseException e) {
            throw new IOException("Incorrect data in API - wrong date format.", e);
        } catch(JSONException e) {
            throw new IOException("Incorrect structure of API's data.", e);
        } catch(IOException e) {
            throw new IOException("Failed to download exchange rates.", e);
        }

        return currencyRates;
    }

    private int daysBetween(Date from, Date to){
        return (int)( (to.getTime() - from.getTime()) / (1000 * 60 * 60 * 24));
    }

    private Date getDateAfterNumberOfDays(Date date, int numberOfDays) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, numberOfDays);
        return calendar.getTime();
    }

    private Comparator<CurrencyRate> getComparator() {
        Comparator<CurrencyRate> comparator = new Comparator<CurrencyRate>() {
            @Override
            public int compare(CurrencyRate comparator1, CurrencyRate comparator2) {
                return comparator1.getDate().compareTo(comparator2.getDate());
            }
        };
        return comparator;
    }

    private SortedSet<CurrencyRate> getCurrencyRates(String currencyCode, Date from, Date to) throws IOException, JSONException, ParseException {
        String url = getUrl(currencyCode, from, to);
        String jsonText = getJsonTextFromNbpApi(url);
        JSONObject json = new JSONObject(jsonText);
        JSONArray rates = json.getJSONArray(jsonRatesField);

        SortedSet<CurrencyRate> currencyRates = new TreeSet<>(getComparator());
        for (int i = 0; i < rates.length(); i++) {
            JSONObject rate = rates.getJSONObject(i);

            Date date = convertToDate(rate.getString(jsonDateField));
            BigDecimal value = BigDecimal.valueOf(rate.getDouble(jsonExchangeRateField));
            CurrencyRate currencyRate = new CurrencyRate(date, value);

            currencyRates.add(currencyRate);
        }
        return currencyRates;
    }

    private String getUrl(String currencyCode, Date from, Date to) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("http://api.nbp.pl/api/exchangerates/rates/a/");
        stringBuilder.append(currencyCode);
        stringBuilder.append("/");
        stringBuilder.append(getStringFromDate(from));
        stringBuilder.append("/");
        stringBuilder.append(getStringFromDate(to));
        stringBuilder.append("/?format=json");

        return stringBuilder.toString();
    }

    private String getStringFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private String getJsonTextFromNbpApi(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String jsonText = getText(br);
        br.close();
        return jsonText;
    }

    private static String getText(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch;
        while((ch = reader.read()) != -1)
            sb.append((char) ch);

        return sb.toString();
    }

    private Date convertToDate(String textDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(textDate);
    }
}
