package by.shylau.currenciesexchange.service;

import by.shylau.currenciesexchange.dto.CurrencyDTOResponce;
import by.shylau.currenciesexchange.model.Currencie;
import org.springframework.stereotype.Service;

@Service
public class FactoryService {
    public Currencie convertCurrencyDTOResponceIntoCurrency(CurrencyDTOResponce currencyDTO) {
        var currencie = new Currencie();
        currencie.setCode(currencyDTO.getCode());
        currencie.setName(currencyDTO.getName());
        currencie.setSign(currencyDTO.getSign());
        System.err.println("33333");
        return currencie;
    }
}
