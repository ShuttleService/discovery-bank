package tech.ioco.discovery.bank.atm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface DenominationRepository extends JpaRepository<Denomination, Integer> {
    Optional<Denomination> findByValue(BigDecimal value);
}
