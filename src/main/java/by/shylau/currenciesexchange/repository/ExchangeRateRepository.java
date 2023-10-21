package by.shylau.currenciesexchange.repository;

import by.shylau.currenciesexchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
    ExchangeRate findByBaseCurrencyIdAndAndTargetCurrencyId(int base, int target);

}

