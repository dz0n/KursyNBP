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
}
