package by.shylau.currenciesexchange.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "exchange_rates")
@Entity
@EqualsAndHashCode
@ToString
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "base_currency_id")
    private int baseCurrencyId;

    @Column(name = "target_currency_id")
    private int targetCurrencyId;

    private Double rate;
}