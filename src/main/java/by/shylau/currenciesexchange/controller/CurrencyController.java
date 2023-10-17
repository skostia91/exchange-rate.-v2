package by.shylau.currenciesexchange.controller;

import by.shylau.currenciesexchange.model.Currencie;
import by.shylau.currenciesexchange.service.CurrencieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrencyController {
    private CurrencieService currenciesService;

    @Autowired
    public CurrencyController(CurrencieService currenciesService) {
        this.currenciesService = currenciesService;
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<Currencie>> getCurrencies() {
        List<Currencie> currencies = currenciesService.findAll();
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

}
