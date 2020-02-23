package tech.ioco.discovery.bank.atm;

public class WithdrawalAllocation {
    private final Denomination denomination;
    private final long count;

    WithdrawalAllocation(Denomination denomination, long count) {
        this.denomination = denomination;
        this.count = count;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public long getCount() {
        return count;
    }
}
