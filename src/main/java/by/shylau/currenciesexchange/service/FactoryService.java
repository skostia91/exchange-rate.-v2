package by.shylau.currenciesexchange.service;

import by.shylau.currenciesexchange.dto.CurrencyDTOResponce;
import by.shylau.currenciesexchange.dto.ExchangeAmountDTO;
import by.shylau.currenciesexchange.dto.ExchangeRateDTORequest;
import by.shylau.currenciesexchange.dto.ExchangeRateDTOResponce;
import by.shylau.currenciesexchange.exception.BadRequestException;
import by.shylau.currenciesexchange.exception.NotFoundException;
import by.shylau.currenciesexchange.model.Currencie;
import by.shylau.currenciesexchange.model.ExchangeRate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class FactoryService {
    private final CurrencieService currencieService;
    private final ExchangeRateService exchangeRateService;
    private final ModelMapper modelMapper;

    @Autowired
    public FactoryService(CurrencieService currencieService, ExchangeRateService exchangeRateService, ModelMapper modelMapper) {
        this.currencieService = currencieService;
        this.exchangeRateService = exchangeRateService;
        this.modelMapper = modelMapper;
    }

    public Currencie convertCurrencyDTOResponceToCurrency(CurrencyDTOResponce currencyDTOResponce) {
        return modelMapper.map(currencyDTOResponce, Currencie.class);
    }

    public List<ExchangeRateDTORequest> getExchangeRateDTORequest(List<ExchangeRate> exchangeRateList) {
        List<ExchangeRateDTORequest> exchangeRateDTORequest = new ArrayList<>();

        for (ExchangeRate exchangeRate : exchangeRateList) {
            exchangeRateDTORequest.add(converterExchangeRateIntoExchangeRateDTO(exchangeRate));
        }
        return exchangeRateDTORequest;
    }

    public ExchangeRateDTORequest converterExchangeRateIntoExchangeRateDTO(ExchangeRate exchangeRate) {
        return new ExchangeRateDTORequest(
                exchangeRate.getId(),
                currencieService.findById(exchangeRate.getBaseCurrencyId()),
                currencieService.findById(exchangeRate.getTargetCurrencyId()),
                exchangeRate.getRate()
        );
    }

    public int convertBaseId(String code) {
        String baseCurrency = getConvertStringBaseCode(code);
        if (currencieService.findByCode(baseCurrency) == null) {
            throw new NotFoundException("не найден обменный курс " + code);
        }
        return currencieService.findByCode(baseCurrency).getId();
    }

    public String getConvertStringBaseCode(String line) {
        if (line.length() != 6) {
            throw new BadRequestException("не корректно указаны валюты");
        }
        return line.substring(0, 3);
    }

    public int convertTargetId(String code) {
        String targetCurrency = getConvertStringTargetCode(code);

        if (currencieService.findByCode(targetCurrency) == null) {
            throw new NotFoundException("не найден обменный курс " + code);
        }
        return currencieService.findByCode(targetCurrency).getId();
    }

    public String getConvertStringTargetCode(String line) {
        if (line.length() < 6) {
            throw new BadRequestException("не указаны валюты");
        }
        return line.substring(3, 6);
    }

    public ExchangeRate convertExchangeDTOIntoExchange(ExchangeRateDTOResponce exchangeRateDTOResponce) {

        int base = currencieService.findByCode(exchangeRateDTOResponce.getBaseCurrencyCode()).getId();
        int target = currencieService.findByCode(exchangeRateDTOResponce.getTargetCurrencyCode()).getId();
        return new ExchangeRate(base, target, exchangeRateDTOResponce.getRate());
    }

    public ExchangeAmountDTO getAmount(String from, String to, String amount) {
        String base = "USD";
        double amountDouble = Double.parseDouble(amount);
        double rate;
        double answer = 0.0;

        if (exchangeRateService.getExchangeRate(convertBaseId(from + to),
                convertTargetId(from + to)) != null) {

            rate = exchangeRateService.getExchangeRate(convertBaseId(from + to),
                    convertTargetId(from + to)).getRate();

            answer = roundDoubles(amountDouble * rate);
        } else {
            if (exchangeRateService.getExchangeRate(convertBaseId(to + from),
                    convertTargetId(to + from)) != null) {

                rate = 1 / exchangeRateService.getExchangeRate(convertBaseId(to + from),
                        convertTargetId(to + from)).getRate();
                answer = roundDoubles(rate * amountDouble);
            } else {

                try {
                    double usdFromRate = exchangeRateService.getExchangeRate(convertBaseId(base + from),
                            convertTargetId(base + from)).getRate();
                    double usdToRate = exchangeRateService.getExchangeRate(convertBaseId(base + to),
                            convertTargetId(base + to)).getRate();
                    rate = 1 / (usdFromRate / usdToRate);
                    answer = roundDoubles(rate * amountDouble);
                } catch (RuntimeException e) {
                    throw new NotFoundException("курса с такими валютами нет");
                }
            }
        }

        return new ExchangeAmountDTO(
                currencieService.findByCode(from),
                currencieService.findByCode(to),
                new BigDecimal(Double.toString(rate)).setScale(2, RoundingMode.HALF_DOWN),
                amount,
                answer);
    }

    private static double roundDoubles(double number) {
        BigDecimal bd = new BigDecimal(Double.toString(number));
        bd = bd.setScale(2, RoundingMode.DOWN);
        return bd.doubleValue();
    }
}