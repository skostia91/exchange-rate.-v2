package by.shylau.currenciesexchange.dto;

import by.shylau.currenciesexchange.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeAmountDTO {
    private Currency base;
    private Currency target;
    private BigDecimal rate;
    private String amount;
    private double convertedAmount;
}
