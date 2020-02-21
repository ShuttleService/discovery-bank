package tech.ioco.discovery.bank.client.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardLimitRepository extends JpaRepository<CreditCardLimit, String> {
}
