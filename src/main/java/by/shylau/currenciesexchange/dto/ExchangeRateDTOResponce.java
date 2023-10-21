package by.shylau.currenciesexchange.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExchangeRateDTOResponce {
    private String baseCurrencyCode;

    private String targetCurrencyCode;

    private Double rate;
}
