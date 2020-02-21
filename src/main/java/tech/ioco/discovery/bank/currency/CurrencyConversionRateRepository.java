package tech.ioco.discovery.bank.currency;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyConversionRateRepository extends JpaRepository<CurrencyConversionRate, String> {
}
