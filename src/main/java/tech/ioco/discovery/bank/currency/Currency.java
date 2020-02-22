package tech.ioco.discovery.bank.currency;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Currency {
    public static final String CODE_USD = "USD";
    public static final String CODE_EURO = "EUR";
    public static final String CODE_ZAR = "ZAR";
    @Id
    @NotNull
    @Size(min = 3, max = 3)
    private final String currencyCode;
    @NotNull
    @Size(min = 1, max = 50)
    private final String description;
    private int decimalPlaces;

    public Currency(String currencyCode, String description, int decimalPlaces) {
        this.currencyCode = currencyCode;
        this.description = description;
        this.decimalPlaces = decimalPlaces;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getDescription() {
        return description;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    //This to please the JPA Gods
    private Currency() {
        this.currencyCode = null;
        this.description = null;
    }
}
