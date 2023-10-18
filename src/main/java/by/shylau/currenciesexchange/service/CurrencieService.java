package by.shylau.currenciesexchange.service;

import by.shylau.currenciesexchange.exception.NotFoundException;
import by.shylau.currenciesexchange.model.Currencie;
import by.shylau.currenciesexchange.repository.CurrenciesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Currencie findByCode(String code) {
        return currenciesRepository.findByCode(code);
    }

    @Transactional()
    public Currencie addCurrencies(Currencie currencie) {
        System.err.println("444");
        currenciesRepository.save(currencie);
        System.err.println("5555");
        return findById(findAll().size());
    }

    public Currencie findById(int id) {
        Currencie currencie = null;
        Optional<Currencie> optional = currenciesRepository.findById(id);

        if (optional.isPresent()) {
            currencie = optional.get();
        }
        return currencie;
    }

}
