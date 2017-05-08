package dzon.kursynbp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by oem on 2017-05-06.
 */

public class CurrenciesListRepository implements  CurrenciesRepository {
    private final SQLiteOpenHelper dbManager;

    public CurrenciesListRepository(Context context) {
        dbManager = new DatabaseManager(context);
    }

    @Override
    public void save(Currency currency) {
        SQLiteDatabase db = dbManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CurrenciesList.COLUMN_NAME_NAME, currency.getName());
        values.put(DatabaseContract.CurrenciesList.COLUMN_NAME_CODE, currency.getCode());

        db.insert(DatabaseContract.CurrenciesList.TABLE_NAME, null, values);
    }

    @Override
    public List<Currency> getAll() {
        SQLiteDatabase db = dbManager.getReadableDatabase();

        String[] columns = {
                DatabaseContract.CurrenciesList.COLUMN_NAME_NAME,
                DatabaseContract.CurrenciesList.COLUMN_NAME_CODE
        };

        String sortOrder = DatabaseContract.CurrenciesList.COLUMN_NAME_NAME + " ASC";

        Cursor cursor = db.query(
                DatabaseContract.CurrenciesList.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<Currency> currencies = new ArrayList<>();
        while(cursor.moveToNext()) {
            Currency currency = new Currency(
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.CurrenciesList.COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.CurrenciesList.COLUMN_NAME_CODE)),
                    Collections.<CurrencyRate>emptyList()
            );
            currencies.add(currency);
        }
        cursor.close();

        return currencies;
    }

    @Override
    public void delete(Currency currency) {
        SQLiteDatabase db = dbManager.getReadableDatabase();

        String selection = DatabaseContract.CurrenciesList.COLUMN_NAME_CODE + " LIKE ?";

        String[] selectionArgs = new String[] {currency.getCode()};

        db.delete(DatabaseContract.CurrenciesList.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = dbManager.getReadableDatabase();

        db.delete(DatabaseContract.CurrenciesList.TABLE_NAME, null, null);
    }
}

