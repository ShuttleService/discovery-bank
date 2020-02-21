package tech.ioco.discovery.bank.client.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAccountRepository extends JpaRepository<ClientAccount, String> {
}
