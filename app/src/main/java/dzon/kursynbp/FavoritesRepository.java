package dzon.kursynbp;

import static dzon.kursynbp.DatabaseContract.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoritesRepository implements CurrenciesRepository {
    private final SQLiteOpenHelper dbManager;

    public FavoritesRepository(Context context) {
        dbManager = new DatabaseManager(context);
    }

    @Override
    public void save(Currency currency) {
        SQLiteDatabase db = dbManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoriteCurrencies.COLUMN_NAME_NAME, currency.getName());
        values.put(FavoriteCurrencies.COLUMN_NAME_CODE, currency.getCode());

        db.insert(FavoriteCurrencies.TABLE_NAME, null, values);
    }

   @Override
   public List<Currency> getAll() {
       SQLiteDatabase db = dbManager.getReadableDatabase();

       String[] columns = {
               FavoriteCurrencies.COLUMN_NAME_NAME,
               FavoriteCurrencies.COLUMN_NAME_CODE
       };

       String sortOrder = FavoriteCurrencies.COLUMN_NAME_NAME + " ASC";

       Cursor cursor = db.query(
               FavoriteCurrencies.TABLE_NAME,
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
                   cursor.getString(cursor.getColumnIndex(FavoriteCurrencies.COLUMN_NAME_NAME)),
                   cursor.getString(cursor.getColumnIndex(FavoriteCurrencies.COLUMN_NAME_CODE)),
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

       String selection = FavoriteCurrencies.COLUMN_NAME_CODE + " LIKE ?";

       String[] selectionArgs = new String[] {currency.getCode()};

       db.delete(FavoriteCurrencies.TABLE_NAME, selection, selectionArgs);
   }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = dbManager.getReadableDatabase();

        db.delete(FavoriteCurrencies.TABLE_NAME, null, null);
    }
}
