package dzon.kursynbp;

import java.io.IOException;
import java.util.Date;
import java.util.List;

interface CurrentExchangeRates {
    List<Currency> getCurrentRates() throws IOException;
}
