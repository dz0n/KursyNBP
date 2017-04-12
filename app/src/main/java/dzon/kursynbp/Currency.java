package dzon.kursynbp;

import java.util.Collection;
import java.util.Collections;

public class Currency {
    private final String name;
    private final String code;
    private final Collection<CurrencyRate> rates;

    public Currency(String name, String code, Collection<CurrencyRate> rates) {
        this.name = name;
        this.code = code;
        this.rates = Collections.unmodifiableCollection(rates);
    }

    public Currency(String name, String code, CurrencyRate rate) {
        this(name, code, Collections.singletonList(rate));
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Collection<CurrencyRate> getRates() {
        return rates;
    }
}
