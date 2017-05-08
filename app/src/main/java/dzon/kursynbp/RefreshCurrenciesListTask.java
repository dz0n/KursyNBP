package dzon.kursynbp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RefreshCurrenciesListTask extends AsyncTask<Void, Void, List<Currency>> {
    private final Context context;

    public RefreshCurrenciesListTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Currency> doInBackground(Void... params) {
        CurrentExchangeRates currentExchangeRates = NbpATableCurrentExchangeRates.getInstance();

        try {
            List<Currency> currencies = currentExchangeRates.getCurrentRates();

            CurrenciesRepository currenciesRepository = new CurrenciesListRepository(context);
            currenciesRepository.deleteAll();

            for(Currency currency : currencies) {
                currenciesRepository.save(currency);
            }

            return currencies;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    @Override
    protected void onPostExecute(List<Currency> currencies) {
        Intent intent = new Intent(context.getString(R.string.receiver_refreshing_currencies_finished));
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
