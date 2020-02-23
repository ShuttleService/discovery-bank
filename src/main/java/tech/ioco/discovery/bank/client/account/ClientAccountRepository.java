package tech.ioco.discovery.bank.client.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import tech.ioco.discovery.bank.client.Client;


import java.math.BigDecimal;

public interface ClientAccountRepository extends JpaRepository<ClientAccount, String> {
    @Query("select ca " +
            "from ClientAccount ca " +
            "where ca.client= :client and ca.accountType.transactional = :transactional  ")
    Page<ClientAccount> findByClient(@Param("client") Client client, @Param("transactional") boolean transactional, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update ClientAccount ca " +
            "set ca.displayBalance = ca.displayBalance - :amount " +
            "where ca = :clientAccount")
    int withdraw(@Param("clientAccount") ClientAccount clientAccount, @Param("amount") BigDecimal amount);
}
