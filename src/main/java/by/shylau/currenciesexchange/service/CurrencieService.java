package by.shylau.currenciesexchange.service;

import by.shylau.currenciesexchange.model.Currencie;
import by.shylau.currenciesexchange.repository.CurrenciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencieService {
    private final CurrenciesRepository currenciesRepository;

    @Autowired
    public CurrencieService(CurrenciesRepository currenciesRepository) {
        this.currenciesRepository = currenciesRepository;
    }

    public List<Currencie> findAll() {
        return currenciesRepository.findAll();
    }


}
