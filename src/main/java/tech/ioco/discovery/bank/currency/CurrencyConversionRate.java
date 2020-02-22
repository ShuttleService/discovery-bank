package tech.ioco.discovery.bank.currency;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
public class CurrencyConversionRate {
    @Id
    @Size(min = 3, max = 3)
    private final String currencyCode;
    @NotNull
    private final BigDecimal rate;
    private final char conversionIndicator;

    public CurrencyConversionRate(String currencyCode, BigDecimal rate, char conversionIndicator) {
        this.currencyCode = currencyCode;
        this.rate = rate;
        this.conversionIndicator = conversionIndicator;
    }

    public BigDecimal getRate() {
        return rate;
    }


    //Pleasing the JPA Gods
    private CurrencyConversionRate() {
        this.conversionIndicator = 0;
        this.rate = null;
        this.currencyCode = null;
    }
}
