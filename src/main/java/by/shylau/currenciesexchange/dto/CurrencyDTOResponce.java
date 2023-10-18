package by.shylau.currenciesexchange.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CurrencyDTOResponce {
    private String code;

    private String name;

    private String sign;
}
