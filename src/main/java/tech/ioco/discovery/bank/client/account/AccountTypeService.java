package tech.ioco.discovery.bank.client.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountTypeService {
    private final Logger logger = LoggerFactory.getLogger(AccountTypeService.class);
    @Autowired
    private AccountTypeRepository repository;

    public void addDefaults() {
        logger.info("Adding default account types to the repo");
        Optional<AccountType> accountType = repository.findById("Savings");
        if (accountType.isEmpty()) {
            logger.info("Adding Saving account type to");
            repository.save(new AccountType("Savings", "Savings Account", true));
        }
        accountType = repository.findById("Cheque");
        if (accountType.isEmpty()) {
            logger.info("Adding cheque account Type");
            repository.save(new AccountType("Cheque", "Cheque Account", true));
        }
        accountType = repository.findById("Credit");
        if (accountType.isEmpty()) {
            logger.info("Adding Credit Account Type");
            repository.save(new AccountType("Credit", "Credit Card", true));
        }
        accountType = repository.findById("Currency");
        if (accountType.isEmpty()) {
            logger.info("Adding Currency Account Type");
            repository.save(new AccountType("Currency", "Currency Account", false));
        }
    }
}
