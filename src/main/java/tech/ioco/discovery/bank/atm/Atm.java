package tech.ioco.discovery.bank.atm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Atm {
    public static final String HEAD_OFFICE_ATM_NAME = "Main";
    public static final String HEAD_OFFICE_ATM_LOCATION = "1 Discovery Place";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int atmId;
    @NotNull
    @Size(min = 1, max = 10)
    private final String name;
    @NotNull
    @Size(min = 1, max = 255)
    private final String location;

    public Atm(String name, String location) {
        this.name = name;
        this.location = location;
    }

    //Pleasing the JPA Gods
    private Atm() {
        this.name = null;
        this.location = null;
    }
}
