package tech.ioco.discovery.bank.atm;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Denomination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int denominationId;
    @NotNull
    private final BigDecimal value;
    @ManyToOne
    @NotNull
    private final DenominationType denominationType;

    public Denomination(BigDecimal value, DenominationType denominationType) {
        this.value = value;
        this.denominationType = denominationType;
    }

    //Pleasing the JPA Gods
    private Denomination() {
        this.value = null;
        this.denominationType = null;
    }
}