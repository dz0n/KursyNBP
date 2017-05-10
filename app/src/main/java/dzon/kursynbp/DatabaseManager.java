package dzon.kursynbp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static dzon.kursynbp.DatabaseContract.*;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ExchangeRates.db";

    private final Context context;

    private static final String SQL_CREATE_CURRENCIES =
            "CREATE TABLE " + CurrenciesList.TABLE_NAME +
            " (" +
            CurrenciesList.COLUMN_NAME_NAME + " TEXT, " +
            CurrenciesList.COLUMN_NAME_CODE + " TEXT" +
            ")";

    private static final String SQL_CREATE_FAVORITES =
            "CREATE TABLE " + FavoriteCurrencies.TABLE_NAME +
                    " (" +
                    FavoriteCurrencies.COLUMN_NAME_NAME + " TEXT, " +
                    FavoriteCurrencies.COLUMN_NAME_CODE + " TEXT" +
                    ")";

    private static final String SQL_DELETE_CURRENCIES =
            "DROP TABLE IF EXISTS " + CurrenciesList.TABLE_NAME;

    private static final String SQL_DELETE_FAVORITES =
            "DROP TABLE IF EXISTS " + FavoriteCurrencies.TABLE_NAME;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CURRENCIES);
        db.execSQL(SQL_CREATE_FAVORITES);

        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('dolar amerykański', 'USD')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('dolar australijski', 'AUD')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('dolar Hongkongu', 'HKD')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('dolar kanadyjski', 'CAD')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('dolar nowozelandzki', 'NZD')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('dolar singapurski', 'SGD')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('euro', 'EUR')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('forint (Węgry)', 'HUF')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('frank szwajcarski', 'CHF')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('funt szterling', 'GBP')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('hrywna (Ukraina)', 'UAH')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('jen (Japonia)', 'JPY')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('korona czeska', 'CZK')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('korona duńska', 'DKK')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('korona islandzka', 'ISK')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('korona norweska', 'NOK')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('korona szwedzka', 'SEK')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('kuna (Chorwacja)', 'HRK')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('lej rumuński', 'RON')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('lew (Bułgaria)', 'BGN')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('lira turecka', 'TRY')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('nowy izraelski szekel', 'ILS')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('peso chilijskie', 'CLP')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('peso filipińskie', 'PHP')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('peso meksykańskie', 'MXN')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('rand (Republika Południowej Afryki)', 'ZAR')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('real (Brazylia)', 'BRL')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('ringgit (Malezja)', 'MYR')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('rubel rosyjski', 'RUB')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('rupia indonezyjska', 'IDR')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('rupia indyjska', 'INR')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('won południowokoreański', 'KRW')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('yuan renminbi (Chiny)', 'CNY')");
        db.execSQL("INSERT INTO [" + CurrenciesList.TABLE_NAME + "] ([" + CurrenciesList.COLUMN_NAME_NAME + "], [" + CurrenciesList.COLUMN_NAME_CODE+ "]) VALUES ('SDR (MFW)', 'XDR')");

        db.execSQL("INSERT INTO [" + FavoriteCurrencies.TABLE_NAME + "] ([" + FavoriteCurrencies.COLUMN_NAME_NAME + "], [" + FavoriteCurrencies.COLUMN_NAME_CODE+ "]) VALUES ('dolar amerykański', 'USD')");
        db.execSQL("INSERT INTO [" + FavoriteCurrencies.TABLE_NAME + "] ([" + FavoriteCurrencies.COLUMN_NAME_NAME + "], [" + FavoriteCurrencies.COLUMN_NAME_CODE+ "]) VALUES ('euro', 'EUR')");
        db.execSQL("INSERT INTO [" + FavoriteCurrencies.TABLE_NAME + "] ([" + FavoriteCurrencies.COLUMN_NAME_NAME + "], [" + FavoriteCurrencies.COLUMN_NAME_CODE+ "]) VALUES ('frank szwajcarski', 'CHF')");
        db.execSQL("INSERT INTO [" + FavoriteCurrencies.TABLE_NAME + "] ([" + FavoriteCurrencies.COLUMN_NAME_NAME + "], [" + FavoriteCurrencies.COLUMN_NAME_CODE+ "]) VALUES ('funt szterling', 'GBP')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CURRENCIES);
        db.execSQL(SQL_DELETE_FAVORITES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
