package dzon.kursynbp;

import android.provider.BaseColumns;

/**
 * Database is denormalized
 */

public final class DatabaseContract {
    private DatabaseContract() {}

    public static class CurrenciesList implements BaseColumns {
        public static final String TABLE_NAME = "currencies";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CODE = "code";
    }

    public static class FavoriteCurrencies implements BaseColumns {
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CODE = "code";
    }
}
