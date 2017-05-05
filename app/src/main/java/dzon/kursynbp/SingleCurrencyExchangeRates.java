package dzon.kursynbp;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;

interface SingleCurrencyExchangeRates {
    SortedSet<CurrencyRate> getExchangeRates(Currency currency, Date from, Date to) throws IOException;
    SortedSet<CurrencyRate> getExchangeRates(String currencyCode, Date from, Date to) throws IOException;
}
