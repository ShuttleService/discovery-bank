package tech.ioco.discovery.bank

import spock.lang.Specification
import tech.ioco.discovery.bank.atm.AtmService
import tech.ioco.discovery.bank.atm.DenominationService
import tech.ioco.discovery.bank.atm.DenominationTypeService
import tech.ioco.discovery.bank.client.ClientService
import tech.ioco.discovery.bank.client.ClientSubTypeService
import tech.ioco.discovery.bank.client.ClientTypeService
import tech.ioco.discovery.bank.client.account.AccountTypeService
import tech.ioco.discovery.bank.client.account.ClientAccountService
import tech.ioco.discovery.bank.currency.CurrencyService

class AppSpec extends Specification {
    private CurrencyService currencyService = Mock()
    private ClientTypeService clientTypeService = Mock()
    private ClientSubTypeService clientSubTypeService = Mock()
    private AccountTypeService accountTypeService = Mock()
    private ClientAccountService clientAccountService = Mock()
    private ClientService clientService = Mock()
    private DenominationTypeService denominationTypeService = Mock()
    private AtmService atmService = Mock()
    private DenominationService denominationService = Mock()

    private App app = new App(currencyService: currencyService, clientService: clientService, clientTypeService: clientTypeService, atmService: atmService,
            clientSubTypeService: clientSubTypeService, accountTypeService: accountTypeService, clientAccountService: clientAccountService,
            denominationTypeService: denominationTypeService, denominationService: denominationService)

    def 'load default currencies, client types, client sub types, account types, clients, denominations and atms on start up'() {
        when: 'starting up'
        app.init()
        then: 'load currencies'
        1 * currencyService.addDefaults()
        then: 'add account types'
        1 * accountTypeService.addDefaults()
        then: 'add client types'
        1 * clientTypeService.addDefaults()
        then: 'add client sub types'
        1 * clientSubTypeService.addDefaults()
        then: 'add clients'
        1 * clientService.addDefaults()
        then: 'add client accounts'
        1 * clientAccountService.addDefaults()
        then: 'add denomination types'
        1 * denominationTypeService.addDefaults()
        then: 'add the atm'
        1 * atmService.addDefaults()
        then: 'add denominations'
        1 * denominationService.addDefaults()
    }
}
