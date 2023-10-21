package by.shylau.currenciesexchange.controller;

import by.shylau.currenciesexchange.dto.ExchangeAmountDTO;
import by.shylau.currenciesexchange.dto.ExchangeRateDTORequest;
import by.shylau.currenciesexchange.dto.ExchangeRateDTOResponce;
import by.shylau.currenciesexchange.exception.BadRequestException;
import by.shylau.currenciesexchange.exception.InternalServerException;
import by.shylau.currenciesexchange.service.CurrencieService;
import by.shylau.currenciesexchange.service.ExchangeRateService;
import by.shylau.currenciesexchange.service.FactoryService;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;
    private final FactoryService factoryService;
    private final CurrencieService currencieService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService, FactoryService factoryService, CurrencieService currencieService) {
        this.exchangeRateService = exchangeRateService;
        this.factoryService = factoryService;
        this.currencieService = currencieService;
    }

    @GetMapping("/exchangeRates")
    public ResponseEntity<List<ExchangeRateDTORequest>> getExchangeRateList() {
        try {
            return new ResponseEntity<>(factoryService.getExchangeRateDTORequest(
                    exchangeRateService.getAllExchangeRates()), HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new InternalServerException("попробуйте ещё раз, бд не доступна");
        }
    }

    @GetMapping("/exchangeRate/{rate}")
    public ResponseEntity<ExchangeRateDTORequest> getRate(@PathVariable("rate") String code) {

        if (code.isBlank()) {
            throw new BadRequestException("не введены данные");
        }
        try {
            return new ResponseEntity<>(factoryService.converterExchangeRateIntoExchangeRateDTO(
                    exchangeRateService.getExchangeRate(factoryService.convertBaseId(code),
                            factoryService.convertTargetId(code))), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new InternalServerException("не удаётся подключиться к бд");
        }
    }

    @PostMapping("/exchangeRates")
    public ResponseEntity<ExchangeRateDTORequest> addExchangeRate(ExchangeRateDTOResponce exchangeRateDTOResponce) {
        if (exchangeRateDTOResponce.getBaseCurrencyCode().isBlank() ||
                exchangeRateDTOResponce.getTargetCurrencyCode().isBlank() ||
                exchangeRateDTOResponce.getRate() <= 0) {
            throw new BadRequestException("не корректно заполнены поля");
        }
        try {
            exchangeRateService.add(factoryService.convertExchangeDTOIntoExchange(exchangeRateDTOResponce));
            return new ResponseEntity<>(getLastElementIntoDB(), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new InternalServerException("не удаётся подключиться к бд");
        }

    }


    @PatchMapping("/exchangeRate/{code}")
    public ResponseEntity<ExchangeRateDTORequest> updateExchangeRate(@PathVariable("code") String code, String rate) {
        try {
            if (code.isBlank() || Double.parseDouble(rate) <= 0) {
                throw new BadRequestException("не корректно введены данные");
            }

        } catch (NumberFormatException e) {
            throw new BadRequestException("не корректно введены данные");
        }

        try {
            var exchangeRate = exchangeRateService.getExchangeRate(
                    factoryService.convertBaseId(code), factoryService.convertTargetId(code));

            exchangeRate.setRate(Double.parseDouble(rate));
            exchangeRateService.add(exchangeRate);
            return new ResponseEntity<>(factoryService.converterExchangeRateIntoExchangeRateDTO(
                    exchangeRateService.getExchangeRate(factoryService.convertBaseId(code),
                            factoryService.convertTargetId(code))), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new InternalServerException("не удаётся подключиться к бд");
        }
    }

    @GetMapping("/exchange")
    public ResponseEntity<ExchangeAmountDTO> getExchangeAmount(@RequestParam("from") String from,
                                                               @RequestParam("to") String to,
                                                               @RequestParam("amount") String amount) {
        if (from == null || to == null || amount.isEmpty() || Double.parseDouble(amount) < 0) {
            throw new BadRequestException("не корректно введены данные");
        }
        return new ResponseEntity<>(factoryService.getAmount(from, to, amount), HttpStatus.OK);
    }



    public ExchangeRateDTORequest getLastElementIntoDB() {
        int size = factoryService.getExchangeRateDTORequest(exchangeRateService.getAllExchangeRates()).size();
        return factoryService.getExchangeRateDTORequest(exchangeRateService.getAllExchangeRates()).get(size - 1);
    }

    //if (exchangeRateRepository.findByBaseCurrencyIdAndAndTargetCurrencyId(base, target) == null) {
    //            throw new NotFoundException("не найден курс");
    //        }
}
