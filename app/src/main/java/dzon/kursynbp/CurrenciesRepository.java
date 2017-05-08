package dzon.kursynbp;

import java.util.List;

interface CurrenciesRepository {
    void save(Currency currency);

    List<Currency> getAll();

    void delete(Currency currency);

    void deleteAll();
}
