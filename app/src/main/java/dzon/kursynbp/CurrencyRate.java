package dzon.kursynbp;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by oem on 2017-04-10.
 */

public class CurrencyRate {
    private final Date date;
    private final BigDecimal rate;

    public CurrencyRate(Date date, BigDecimal rate) {
        this.date = date;
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyRate that = (CurrencyRate) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return rate != null ? rate.equals(that.rate) : that.rate == null;

    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        return result;
    }
}
