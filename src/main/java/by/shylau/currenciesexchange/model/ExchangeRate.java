package by.shylau.currenciesexchange.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

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

    private double rate;

    public ExchangeRate(int baseCurrencyId, int targetCurrencyId, double rate) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
    }
}