package dzon.kursynbp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CurrentRatesTask extends AsyncTask<Void, Void, List<Currency>> {
    private final CurrentExchangeRates ratesDownloader = NbpATableCurrentExchangeRates.getInstance();
    private final ListView listView;
    private final TextView dateView;
    private final List<String> currenciesCodes;

    public CurrentRatesTask(ListView listView, TextView dateView, List<String> currenciesCodes) {
        this.listView = listView;
        this.dateView = dateView;
        this.currenciesCodes = Collections.unmodifiableList(currenciesCodes);
    }

    @Override
    protected List<Currency> doInBackground(Void... params) {
        if(currenciesCodes.size()==0)
            return Collections.emptyList();

        try {
            List<Currency> currencies = ratesDownloader.getCurrentRates();
            Log.d("debugging", "currencies.size()=" + currencies.size());
            return currencies;
        } catch (IOException e) {
            Log.d("debugging", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    protected void onPostExecute(List<Currency> currencies) {
        List<String> currenciesList = new ArrayList<>();

        if (currenciesCodes.size() == 0) {
            currenciesList.add(getNoCurrenciesSet());
        } else {
            for (Currency currency : currencies) {
                if (currenciesCodes.contains(currency.getCode())) {
                    currenciesList.add(currency.getCode() + ": " + currency.getRates().iterator().next().getRate() + " (" + currency.getName() + ")");
                }
            }

            if(currenciesList.size()==0) {
                currenciesList.add(getFailureText());
            } else {
                if(currencies.size() > 0) {
                    setDateInView(currencies);
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(listView.getContext(),
                android.R.layout.simple_list_item_1,
                currenciesList);
        listView.setAdapter(adapter);
    }

    private void setDateInView(List<Currency> currencies) {
        Date date = currencies.get(0).getRates().iterator().next().getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateView.setText(sdf.format(date));
    }

    private String getFailureText() {
        return dateView.getContext().getString(R.string.failure_download);
    }

    private String getNoCurrenciesSet() {
        return dateView.getContext().getString(R.string.no_currencies);
    }
}
