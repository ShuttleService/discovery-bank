package tech.ioco.discovery.bank.client.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ioco.discovery.bank.client.Client;
import tech.ioco.discovery.bank.client.ClientRepository;
import tech.ioco.discovery.bank.currency.Currency;
import tech.ioco.discovery.bank.currency.CurrencyRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ClientAccountService {
    private static final Logger logger = LoggerFactory.getLogger(ClientAccountService.class);
    @Autowired
    private ClientAccountRepository repository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CreditCardLimitRepository creditCardLimitRepository;

    public void addDefaults() {
        logger.info("Adding default client accounts");
        final String test = "test";
        Optional<ClientAccount> testClientAccount = repository.findById(test);
        if (testClientAccount.isEmpty()) {
            logger.info("Adding a test client account to the repository");
            Optional<Client> testClient = clientRepository.findByUsername(test);
            Optional<Currency> zar = currencyRepository.findById("ZAR");
            Optional<AccountType> cheque = accountTypeRepository.findById("Cheque");
            final ClientAccount testAccount = new ClientAccount(test, testClient.get(), cheque.get(), zar.get(), BigDecimal.ZERO);
            repository.save(testAccount);
        }
    }
}
