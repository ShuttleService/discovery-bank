package tech.ioco.discovery.bank.atm;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class DenominationType {
    @Id
    @NotNull
    @Size(min = 1, max = 1)
    private final String denominationTypeCode;
    @NotNull
    @Size(min = 1, max = 255)
    private final String description;

    public DenominationType(String denominationTypeCode, String description) {
        this.denominationTypeCode = denominationTypeCode;
        this.description = description;
    }

    //Pleasing the JPA Gods
    private DenominationType() {
        this.denominationTypeCode = null;
        this.description = null;
    }
}
