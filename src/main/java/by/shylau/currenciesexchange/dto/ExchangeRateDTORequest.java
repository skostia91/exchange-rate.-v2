package by.shylau.currenciesexchange.dto;

import by.shylau.currenciesexchange.model.Currencie;
import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDTORequest {
    private int id;

    private Currencie baseCurrencyId;

    private Currencie targetCurrencyId;

    private Double rate;

    public ExchangeRateDTORequest(Currencie baseCurrencyId, Currencie targetCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
    }

    public ExchangeRateDTORequest(Currencie baseCurrencyId, Currencie targetCurrencyId, Double rate) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
    }
}