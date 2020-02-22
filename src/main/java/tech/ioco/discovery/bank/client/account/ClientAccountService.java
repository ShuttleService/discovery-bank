package tech.ioco.discovery.bank.client.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.ioco.discovery.bank.Book;
import tech.ioco.discovery.bank.client.Client;
import tech.ioco.discovery.bank.client.ClientRepository;
import tech.ioco.discovery.bank.currency.Currency;
import tech.ioco.discovery.bank.currency.CurrencyConversionRate;
import tech.ioco.discovery.bank.currency.CurrencyConversionRateRepository;
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
    private CurrencyConversionRateRepository currencyConversionRateRepository;

    public Page<ClientAccount> currencyAccounts(Client client, Book book) {
        logger.info("Finding currency accounts for client {}, Book {} ", client, book);
        Pageable pageable = PageRequest.of(book.getPageNumber(), book.getItemsPerPage(), Sort.by(Sort.Order.desc("displayBalance")));
        Page<ClientAccount> page = repository.findByClient(client, false, pageable);
        //The ZAR currency is pre loaded and thus will always be in the repository.
        final Currency zar = currencyRepository.findById(Currency.CODE_ZAR).get();
        page.getContent().forEach(clientAccount -> {
            Optional<CurrencyConversionRate> currencyConversionRate = currencyConversionRateRepository.findById(clientAccount.getCurrency().getCurrencyCode());
            if (currencyConversionRate.isPresent()) {
                BigDecimal convertedAmount = currencyConversionRate.get().getRate().multiply(clientAccount.getDisplayBalance());
                CurrencyDisplayBalance currencyDisplayBalance = new CurrencyDisplayBalance(zar, currencyConversionRate.get().getRate(), convertedAmount);
                clientAccount.currencyBalance(currencyDisplayBalance);
            } else {
                logger.warn("CurrencyConversionRate for currency {} is unavailable", clientAccount.getCurrency());
            }
        });
        return page;
    }

    public void addDefaults() {
        logger.info("Adding default client accounts");
        final String test = "test";
        Optional<ClientAccount> testClientAccount = repository.findById(test);
        Optional<Client> testClient = clientRepository.findByUsername(test);
        Optional<Currency> zar = currencyRepository.findById("ZAR");
        if (testClientAccount.isEmpty()) {
            logger.info("Adding a test client cheque account to the repository");
            Optional<AccountType> cheque = accountTypeRepository.findById("Cheque");
            final ClientAccount testAccount = new ClientAccount(test, testClient.get(), cheque.get(), zar.get(), BigDecimal.ZERO);
            repository.save(testAccount);
        }

        testClientAccount = repository.findById("testC");
        if (testClientAccount.isEmpty()) {
            logger.info("Adding a test client Credit account to the repository");
            Optional<AccountType> credit = accountTypeRepository.findById("Credit");
            final ClientAccount testAccount = new ClientAccount(test, testClient.get(), credit.get(), zar.get(), BigDecimal.ZERO);
            repository.save(testAccount);
        }

        testClientAccount = repository.findById("testS");
        if (testClientAccount.isEmpty()) {
            logger.info("Adding a test client Savings account to the repository");
            Optional<AccountType> savings = accountTypeRepository.findById("Savings");
            final ClientAccount testAccount = new ClientAccount(test, testClient.get(), savings.get(), zar.get(), BigDecimal.ZERO);
            repository.save(testAccount);
        }

        testClientAccount = repository.findById("currency");

        if (testClientAccount.isEmpty()) {
            logger.info("Adding a test client Currency account to the repository");
            Optional<AccountType> currency = accountTypeRepository.findById("Currency");
            Optional<Currency> usd = currencyRepository.findById("USD");
            final ClientAccount testAccount = new ClientAccount(test, testClient.get(), currency.get(), usd.get(), BigDecimal.ZERO);
            repository.save(testAccount);
        }
    }

}
