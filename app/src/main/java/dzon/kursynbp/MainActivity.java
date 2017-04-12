package dzon.kursynbp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_currency:
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //getActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.listViewCurrentRates);
        TextView dateView = (TextView) findViewById(R.id.dateView);
        printCurrentExchangeRates(listView, dateView);
    }

    private void printCurrentExchangeRates(ListView listView, TextView dateView) {
        List<String> currenciesCodes = new ArrayList<>();
        currenciesCodes.add("USD");
        currenciesCodes.add("EUR");
        currenciesCodes.add("GBP");
        currenciesCodes.add("RUB");
        currenciesCodes.add("UAH");
        currenciesCodes.add("DKK");
        currenciesCodes.add("CAD");
        currenciesCodes.add("CZK");
        currenciesCodes.add("HUF");
        currenciesCodes.add("JPY");
        currenciesCodes.add("CHF");
        CurrentRatesTask currentRatesTask = new CurrentRatesTask(listView, dateView, currenciesCodes);
        currentRatesTask.execute();
    }
}
