package tech.ioco.discovery.bank.client.account

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import spock.lang.Issue
import spock.lang.Specification
import tech.ioco.discovery.bank.Book
import tech.ioco.discovery.bank.SpecFactory
import tech.ioco.discovery.bank.client.ClientRepository
import tech.ioco.discovery.bank.currency.Currency
import tech.ioco.discovery.bank.currency.CurrencyConversionRateRepository
import tech.ioco.discovery.bank.currency.CurrencyRepository


class ClientAccountServiceSpec extends Specification {
    private ClientAccountRepository repository = Mock()
    private ClientRepository clientRepository = Mock()
    private AccountTypeRepository accountTypeRepository = Mock()

    private CurrencyRepository currencyRepository = Mock()
    private CurrencyConversionRateRepository currencyConversionRateRepository = Mock()
    private ClientAccountService service = new ClientAccountService(repository: repository, clientRepository: clientRepository,
            accountTypeRepository: accountTypeRepository, currencyRepository: currencyRepository,
            currencyConversionRateRepository: currencyConversionRateRepository)

    def 'add a default client account should it not be in the repository'() {
        when: 'adding default accounts'
        service.addDefaults()
        then: 'get the test client'
        1 * clientRepository.findByUsername('test') >> Optional.of(SpecFactory.client)
        and: 'get a currency'
        1 * currencyRepository.findById('ZAR') >> Optional.of(SpecFactory.currency)
        and: 'get the cheque account'
        1 * accountTypeRepository.findById('Cheque') >> Optional.of(SpecFactory.accountTypeCheque)
        and: 'the test client cheque account is not in the repository'
        1 * repository.findById('test') >> Optional.empty()
        then: 'save the client account'
        1 * repository.save({ ClientAccount account ->
            account.clientAccountNumber == 'test' && account.client == SpecFactory.client &&
                    account.accountType == SpecFactory.accountTypeCheque && account.currency == SpecFactory.currency
        })
        and: 'get the client credit card account type'
        1 * repository.findById('testC') >> Optional.empty()
        and: 'get the credit card  account'
        1 * accountTypeRepository.findById('Credit') >> Optional.of(SpecFactory.accountTypeCreditCard)
        then: 'save the client credit card account'
        1 * repository.save({ ClientAccount account ->
            account.clientAccountNumber == 'test' && account.client == SpecFactory.client &&
                    account.accountType == SpecFactory.accountTypeCreditCard && account.currency == SpecFactory.currency
        })
        and: 'get the client  savings account type'
        1 * repository.findById('testS') >> Optional.empty()
        and: 'get the savings account'
        1 * accountTypeRepository.findById('Savings') >> Optional.of(SpecFactory.accountTypeSaving)
        then: 'save the client savings account'
        1 * repository.save({ ClientAccount account ->
            account.clientAccountNumber == 'test' && account.client == SpecFactory.client &&
                    account.accountType == SpecFactory.accountTypeSaving && account.currency == SpecFactory.currency
        })
        and: 'get the currency  account type'
        1 * repository.findById('currency') >> Optional.empty()
        and: 'get the usd account type'
        1 * currencyRepository.findById('USD') >> Optional.of(SpecFactory.currencyUS)
        and: 'get the currency account'
        1 * accountTypeRepository.findById('Currency') >> Optional.of(SpecFactory.accountTypeCurrency)
        then: 'save the client savings account'
        1 * repository.save({ ClientAccount account ->
            account.clientAccountNumber == 'test' && account.client == SpecFactory.client &&
                    account.accountType == SpecFactory.accountTypeCurrency && account.currency == SpecFactory.currencyUS
        })
    }

    def 'skip adding a test client account should it be in the repository'() {
        when: 'adding default accounts'
        service.addDefaults()
        then: 'the cheque account is in the repository'
        1 * repository.findById('test') >> Optional.of(SpecFactory.clientAccount)
        and: 'skip saving the client cheque account'
        0 * repository.save({ ClientAccount account -> account.accountType.accountTypeCode == 'Cheque' })
        and: 'the Credit Card account is in the repository'
        1 * repository.findById('testC') >> Optional.of(SpecFactory.clientAccount)
        and: 'skip saving the client credit account'
        0 * repository.save({ ClientAccount account -> account.accountType.accountTypeCode == 'Credit' })
        and: 'the Savings account is in the repository'
        1 * repository.findById('testS') >> Optional.of(SpecFactory.clientAccount)
        and: 'skip saving the client savings account'
        0 * repository.save({ ClientAccount account -> account.accountType.accountTypeCode == 'Savings' })
        1 * repository.findById('currency') >> Optional.of(SpecFactory.clientAccount)
        and: 'skip saving the client currency account'
        0 * repository.save({ ClientAccount account -> account.accountType.accountTypeCode == 'Currency' })
    }

    @Issue('4.2.2. Display currency accounts with converted Rand values')
    def 'Get client currency accounts, convert the amount to the rand equivalent value '() {
        when: 'getting currency accounts'
        Page<ClientAccount> page = service.currencyAccounts(SpecFactory.client, new Book(0, 17))
        then: 'the repository finds currency accounts'
        1 * repository.findByClient(SpecFactory.client, false, { Pageable pageable ->
            pageable.sort.stream().allMatch({
                Sort.Order order -> order.descending && order.property == 'displayBalance'
            })
        }) >> new PageImpl<>([SpecFactory.clientAccountUS, SpecFactory.clientAccountEURO])
        then: 'get the ZAR currency'
        1 * currencyRepository.findById(Currency.CODE_ZAR) >> Optional.of(SpecFactory.currencyZAR)
        then: 'get the currency conversion rate for each currency'
        1 * currencyConversionRateRepository.findById(SpecFactory.currencyUS.currencyCode) >> Optional.of(SpecFactory.currencyConversionRateUS)
        1 * currencyConversionRateRepository.findById(SpecFactory.currencyEURO.currencyCode) >> Optional.of(SpecFactory.currencyConversionRateEURO)
        then: 'all currency accounts for the user are returned'
        page.totalElements == 2
        when: 'getting the individual client accounts'
        ClientAccount usdClientAccount = page.content.stream().filter({
            ClientAccount account -> account.currency.currencyCode == Currency.CODE_USD
        }).findFirst().get()
        ClientAccount euroClientAccount = page.content.stream().filter({
            ClientAccount account -> account.currency.currencyCode == Currency.CODE_EURO
        }).findFirst().get()
        then: 'the usd client account has been converted to ZAR using the usd rate'
        usdClientAccount.currencyDisplayBalance.get().currency == SpecFactory.currencyZAR
        usdClientAccount.currencyDisplayBalance.get().displayBalance == SpecFactory.clientAccountUS.displayBalance * SpecFactory.currencyConversionRateUS.rate
        usdClientAccount.currencyDisplayBalance.get().rate == SpecFactory.currencyConversionRateUS.rate
        and: 'the euro client account has been converted to ZAR'
        euroClientAccount.currencyDisplayBalance.get().currency == SpecFactory.currencyZAR
        euroClientAccount.currencyDisplayBalance.get().displayBalance == SpecFactory.clientAccountEURO.displayBalance * SpecFactory.currencyConversionRateEURO.rate
        euroClientAccount.currencyDisplayBalance.get().rate == SpecFactory.currencyConversionRateEURO.rate
    }

    @Issue('4.2.2. Display currency accounts with converted Rand values. Error condition')
    def 'return an empty currency balance when the currency conversion for a currency is unavailable'() {
        when: 'getting currency accounts'
        Page<ClientAccount> page = service.currencyAccounts(SpecFactory.client, new Book(0, 1))
        then: 'the repository finds currency accounts'
        1 * repository.findByClient(_, _, _) >> new PageImpl<>([SpecFactory.clientAccount])
        then: 'get the ZAR currency'
        1 * currencyRepository.findById(Currency.CODE_ZAR) >> Optional.of(SpecFactory.currencyZAR)
        then: 'currency conversion rates for currencies are unavailable'
        1 * currencyConversionRateRepository.findById(_) >> Optional.empty()
        then: 'return currency accounts with no conversions'
        page.content.stream().allMatch({ ClientAccount clientAccount -> clientAccount.currencyDisplayBalance.empty })
    }

}
