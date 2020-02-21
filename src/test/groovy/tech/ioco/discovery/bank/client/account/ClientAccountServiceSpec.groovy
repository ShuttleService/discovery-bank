package tech.ioco.discovery.bank.client.account

import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory
import tech.ioco.discovery.bank.client.ClientRepository
import tech.ioco.discovery.bank.currency.CurrencyRepository

class ClientAccountServiceSpec extends Specification {
    private ClientAccountRepository repository = Mock()
    private ClientRepository clientRepository = Mock()
    private AccountTypeRepository accountTypeRepository = Mock()
    private CreditCardLimitRepository creditCardLimitRepository = Mock()
    private CurrencyRepository currencyRepository = Mock()
    private ClientAccountService service = new ClientAccountService(repository: repository, clientRepository: clientRepository, accountTypeRepository: accountTypeRepository, creditCardLimitRepository: creditCardLimitRepository, currencyRepository: currencyRepository)

    def 'add a default client account should it not be in the repository'() {
        when: 'adding default accounts'
        service.addDefaults()
        then: 'the test client is not in the repository'
        1 * repository.findById('test') >> Optional.empty()
        and: 'get the test client'
        1 * clientRepository.findByUsername('test') >> Optional.of(SpecFactory.client)
        and: 'get an account type'
        1 * accountTypeRepository.findById('Cheque') >> Optional.of(SpecFactory.accountType)
        and: 'get a currency'
        1 * currencyRepository.findById('ZAR') >> Optional.of(SpecFactory.currency)
        then: 'save the client account'
        1 * repository.save({ ClientAccount account ->
            account.clientAccountNumber == 'test' && account.client == SpecFactory.client &&
                    account.accountType == SpecFactory.accountType && account.currency == SpecFactory.currency
        })
    }

    def 'skip adding a test client account should it be in the repository'() {
        when: 'adding default accounts'
        service.addDefaults()
        then: 'the test client is not in the repository'
        1 * repository.findById('test') >> Optional.of(SpecFactory.clientAccount)
        and: 'skip getting the test client'
        0 * clientRepository.findByUsername(_)
        and: 'skip getting an account type'
        0 * accountTypeRepository.fixxndById(_)
        and: 'skip getting a currency'
        0 * currencyRepository.findById(_)
        and: 'skip saving the client account'
        0 * repository.save(_)
    }

}
