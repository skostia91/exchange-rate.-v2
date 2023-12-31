package by.shylau.currenciesexchange.controller;

import by.shylau.currenciesexchange.dto.CurrencyDTOResponce;
import by.shylau.currenciesexchange.exception.BadRequestException;
import by.shylau.currenciesexchange.exception.ConflictException;
import by.shylau.currenciesexchange.exception.InternalServerException;
import by.shylau.currenciesexchange.exception.NotFoundException;
import by.shylau.currenciesexchange.model.Currencie;
import by.shylau.currenciesexchange.service.CurrencieService;
import by.shylau.currenciesexchange.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CurrencyController {
    private final CurrencieService currenciesService;
    private final FactoryService factoryService;

    @Autowired
    public CurrencyController(CurrencieService currenciesService, FactoryService factoryService) {
        this.currenciesService = currenciesService;
        this.factoryService = factoryService;
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<Currencie>> getCurrencies() {
        try {
            return new ResponseEntity<>(currenciesService.findAll(), HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new InternalServerException("ошибка подключения к бд");
        }
    }

    @GetMapping("/currency/{currency}")
    public ResponseEntity<Currencie> getCurrencyByCode(@PathVariable("currency") String code) {
        if (code.isBlank()) {
            throw new BadRequestException("код валюты отсутствует в адресе");
        }
        try {
            if (currenciesService.findByCode(code) == null) {
                throw new NotFoundException("валюта = " + code + " не найдена");
            }
            return new ResponseEntity<>(currenciesService.findByCode(code), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new InternalServerException("не удаётся подключиться к бд");
        }
    }

    @PostMapping("/currencies")
    public ResponseEntity<Currencie> addCurrencies(CurrencyDTOResponce currencieDTO) {
        if (currencieDTO.getCode().isEmpty() || currencieDTO.getName().isEmpty() ||
                currencieDTO.getSign().isEmpty()) {
            throw new BadRequestException("отсутствует нужное поле формы");
        }
        try {
            return new ResponseEntity<>(currenciesService.addCurrencies(factoryService.convertCurrencyDTOResponceToCurrency(currencieDTO)),
                    HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new ConflictException("валюта с таким именем уже существует");
        } catch (RuntimeException e) {
            throw new InternalServerException("не удаётся подключиться к бд");
        }
    }
}