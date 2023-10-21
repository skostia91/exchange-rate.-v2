package by.shylau.currenciesexchange.dto;

import by.shylau.currenciesexchange.model.Currencie;
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
    private Currencie base;
    private Currencie target;
    private BigDecimal rate;
    private String amount;
    private double convertedAmount;
}
