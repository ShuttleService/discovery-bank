package tech.ioco.discovery.bank.atm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtmRepository extends JpaRepository<Atm, Integer> {
    Optional<Atm> findByName(String name);
}
