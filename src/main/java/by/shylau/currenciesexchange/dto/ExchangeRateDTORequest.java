package by.shylau.currenciesexchange.dto;

import by.shylau.currenciesexchange.model.Currency;
import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDTORequest {
    private int id;

    private Currency baseCurrencyId;

    private Currency targetCurrencyId;

    private Double rate;

    public ExchangeRateDTORequest(Currency baseCurrencyId, Currency targetCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
    }

    public ExchangeRateDTORequest(Currency baseCurrencyId, Currency targetCurrencyId, Double rate) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
    }
}