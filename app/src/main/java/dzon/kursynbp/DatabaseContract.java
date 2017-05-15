package dzon.kursynbp;

import android.provider.BaseColumns;

/**
 * Database is denormalized
 */

final class DatabaseContract {
    private DatabaseContract() {}

    static class CurrenciesList implements BaseColumns {
        static final String TABLE_NAME = "currencies";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_CODE = "code";
    }

    static class FavoriteCurrencies implements BaseColumns {
        static final String TABLE_NAME = "favorites";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_CODE = "code";
    }
}
