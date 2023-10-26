package by.shylau.currenciesexchange.service;

import by.shylau.currenciesexchange.model.Currency;
import by.shylau.currenciesexchange.repository.CurrencyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code);
    }

    @Transactional()
    public Currency addCurrencies(Currency currency) throws RuntimeException {
        return currencyRepository.save(currency);
    }

    public Currency findById(int id) {
        Currency currency = null;
        Optional<Currency> optional = currencyRepository.findById(id);

        if (optional.isPresent()) {
            currency = optional.get();
        }
        return currency;
    }
}