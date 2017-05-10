package dzon.kursynbp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setCurrenciesList();
        }
    };

    private List<Currency> currencies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(getApplicationContext().getString(R.string.receiver_refreshing_currencies_finished)));

        setCurrenciesList();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveFavorites();
    }

    public void refreshCurrencies(View view) {
        RefreshCurrenciesListTask refreshCurrenciesListTask = new RefreshCurrenciesListTask(getApplicationContext());
        refreshCurrenciesListTask.execute();
    }

    public void setCurrenciesList() {
        CurrenciesRepository currenciesListRepository = new CurrenciesListRepository(getApplicationContext());
        CurrenciesRepository favoritesRepository = new FavoritesRepository(getApplicationContext());

        currencies = currenciesListRepository.getAll();
        List<Currency> favorites = favoritesRepository.getAll();

        Collections.sort(currencies, getComparator());

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.listLayout);
        viewGroup.removeAllViewsInLayout();

        for(int i = 0; i < currencies.size(); i++) {
            Currency currency = currencies.get(i);
            CheckedTextView checkedTextView = (CheckedTextView) getLayoutInflater().inflate(R.layout.ctv_template, null);
            checkedTextView.setText(currency.getCode() + " (" + currency.getName() + ")");
            checkedTextView.setId(i);
            checkedTextView.setOnClickListener(this);
            if(favorites.contains(currency)) {
                checkedTextView.setChecked(true);
            } else {
                checkedTextView.setChecked(false);
            }
            viewGroup.addView(checkedTextView);
        }
    }

    private Comparator<Currency> getComparator() {
        return new Comparator<Currency>() {
            @Override
            public int compare(Currency o1, Currency o2) {
                return o1.getCode().compareTo(o2.getCode());
            }
        };
    }

    @Override
    public void onClick(View v) {
        CheckedTextView checkedTextView = (CheckedTextView) v;
        checkedTextView.toggle();
    }

    private void saveFavorites() {
        CurrenciesRepository favoritesRepository = new FavoritesRepository(getApplicationContext());
        favoritesRepository.deleteAll();

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.listLayout);
        for(int i=0; i<viewGroup.getChildCount(); i++) {
            if(((CheckedTextView) viewGroup.getChildAt(i)).isChecked()) {
                favoritesRepository.save(currencies.get(i));
            }
        }
    }
}
