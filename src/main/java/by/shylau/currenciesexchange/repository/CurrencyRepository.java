package by.shylau.currenciesexchange.repository;

import by.shylau.currenciesexchange.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Currency findByCode(String name);
}
