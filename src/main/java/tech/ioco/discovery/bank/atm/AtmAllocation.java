package tech.ioco.discovery.bank.atm;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class AtmAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int atmAllocationId;
    @NotNull
    @OneToOne
    @JoinColumn(name = "atm_id")
    private final Atm atm;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "denomination_id")
    private final Denomination denomination;
    private final int count;

    public AtmAllocation(Atm atm, Denomination denomination, int count) {
        this.atm = atm;
        this.denomination = denomination;
        this.count = count;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public int getCount() {
        return count;
    }

    //Pleasing the JPA Gods
    private AtmAllocation() {
        this.atm = null;
        this.denomination = null;
        this.count = 0;
    }
}
