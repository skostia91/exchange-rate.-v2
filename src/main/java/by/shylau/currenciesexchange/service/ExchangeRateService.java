package by.shylau.currenciesexchange.service;

import by.shylau.currenciesexchange.exception.ConflictException;
import by.shylau.currenciesexchange.model.ExchangeRate;
import by.shylau.currenciesexchange.repository.ExchangeRateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public List<ExchangeRate> getAllExchangeRates() {
        return exchangeRateRepository.findAll();
    }

    public ExchangeRate getExchangeRate(int base, int target) {
        return exchangeRateRepository.findByBaseCurrencyIdAndAndTargetCurrencyId(base, target);
    }

    public ExchangeRate add(ExchangeRate exchangeRate) {
        try {
            return exchangeRateRepository.save(exchangeRate);
        } catch (RuntimeException e) {
            throw new ConflictException("валютная пара с таким кодом уже существует");
        }
    }
}