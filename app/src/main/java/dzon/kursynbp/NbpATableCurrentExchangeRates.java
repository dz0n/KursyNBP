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
import java.util.Date;
import java.util.List;

class NbpATableCurrentExchangeRates implements CurrentExchangeRates {
    private final String currentRatesUrl = "http://api.nbp.pl/api/exchangerates/tables/a/?format=json";
    private final String jsonRatesField = "rates";
    private final String jsonNameField = "currency";
    private final String jsonCodeField = "code";
    private final String jsonExchangeRateField = "mid";
    private final String jsonDateField = "effectiveDate";

    private NbpATableCurrentExchangeRates() {}

    public static synchronized NbpATableCurrentExchangeRates getInstance() {
        return new NbpATableCurrentExchangeRates();
    }

    @Override
    public List<Currency> getCurrentRates() throws IOException {
        try {
            String jsonText = getJsonTextFromNbpApi();
            Date date = getDate(jsonText);
            JSONArray jsonRates = getRatesFromJsonText(jsonText);

            ArrayList<Currency> currencies = new ArrayList<>();
            for (int i = 0; i < jsonRates.length(); i++) {
                JSONObject jsonRate = jsonRates.getJSONObject(i);

                CurrencyRate currencyRate = new CurrencyRate(date, BigDecimal.valueOf(jsonRate.getDouble(jsonExchangeRateField)));
                Currency currency = new Currency(jsonRate.getString(jsonNameField), jsonRate.getString(jsonCodeField), currencyRate);

                currencies.add(currency);
            }

            return currencies;
        } catch(ParseException e) {
            throw new IOException("Incorrect data in API - wrong date format.", e);
        } catch(JSONException e) {
            throw new IOException("Incorrect structure of API's data.", e);
        } catch(IOException e) {
            throw new IOException("Failed to download current exchange rates.", e);
        }
    }

    private String getJsonTextFromNbpApi() throws IOException {
        InputStream is = new URL(currentRatesUrl).openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String jsonText = getText(br);
        br.close();
        return jsonText;
    }

    private Date getDate(String jsonText) throws JSONException, ParseException {
        JSONArray dayTables = new JSONArray(jsonText);
        JSONObject dayTable = dayTables.getJSONObject(0);

        String dateText = dayTable.getString(jsonDateField);

        return convertToDate(dateText);
    }

    private JSONArray getRatesFromJsonText(String jsonText) throws JSONException {
        JSONArray dayTables = new JSONArray(jsonText);
        JSONObject dayTable = dayTables.getJSONObject(0);

        return dayTable.getJSONArray(jsonRatesField);
    }

    private Date convertToDate(String textDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(textDate);
    }

    private static String getText(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch;
        while((ch = reader.read()) != -1)
            sb.append((char) ch);

        return sb.toString();
    }
}
