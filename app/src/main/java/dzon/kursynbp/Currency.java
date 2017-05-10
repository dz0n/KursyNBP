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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        if (name != null ? !name.equals(currency.name) : currency.name != null) return false;
        return code != null ? code.equals(currency.code) : currency.code == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }
}
