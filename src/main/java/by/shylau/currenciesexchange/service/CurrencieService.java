package by.shylau.currenciesexchange.service;

import by.shylau.currenciesexchange.repository.CurrenciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencieService {
    private CurrenciesRepository currenciesRepository;

    @Autowired
    public CurrencieService(CurrenciesRepository currenciesRepository) {
        this.currenciesRepository = currenciesRepository;
    }


}
