package dzon.kursynbp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_currency:
                intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        printCurrentExchangeRates();
    }

    private void printCurrentExchangeRates() {
        CurrenciesRepository favoritesRepository = new FavoritesRepository(getApplicationContext());
        List<Currency> currencies = favoritesRepository.getAll();

        Collections.sort(currencies, getComparator());

        List<String> currenciesCodes = new ArrayList<>();
        for(Currency currency : currencies) {
            currenciesCodes.add(currency.getCode());
        }

        ListView listView = (ListView) findViewById(R.id.listViewCurrentRates);
        TextView dateView = (TextView) findViewById(R.id.dateView);

        CurrentRatesTask currentRatesTask = new CurrentRatesTask(listView, dateView, currenciesCodes);
        currentRatesTask.execute();
    }

    private Comparator<Currency> getComparator() {
        return new Comparator<Currency>() {
            @Override
            public int compare(Currency o1, Currency o2) {
                return o1.getCode().compareTo(o2.getCode());
            }
        };
    }
}
