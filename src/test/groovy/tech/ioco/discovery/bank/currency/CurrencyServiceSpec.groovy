package tech.ioco.discovery.bank.currency

import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory


class CurrencyServiceSpec extends Specification {
    private CurrencyRepository repository = Mock()
    private CurrencyService service = new CurrencyService(repository: repository)

    def 'Add default currencies'() {
        when: 'adding default currencies'
        service.addDefaults()
        then: 'USD is not in the repository'
        1 * repository.findById('USD') >> Optional.empty()
        then: 'add USD to the repository'
        1 * repository.save({ Currency currency -> currency.currencyCode == 'USD' && currency.description == 'United States Dollar' && currency.decimalPlaces == 2 })
        then: 'ZAR is not in the repository'
        1 * repository.findById('ZAR') >> Optional.empty()
        then: 'add ZAR to the repository'
        1 * repository.save({ Currency currency -> currency.currencyCode == 'ZAR' && currency.description == 'South African Rand' && currency.decimalPlaces == 2 })
        then: 'EUR is not in the repository'
        1 * repository.findById('EUR') >> Optional.empty()
        then: 'add ZAR to the repository'
        1 * repository.save({ Currency currency -> currency.currencyCode == 'EUR' && currency.description == 'Euro' && currency.decimalPlaces == 2 })
    }

    def 'only add default currencies that are not already in the repository'() {
        when: 'adding default currencies'
        service.addDefaults()
        then: 'USD is in the repository'
        1 * repository.findById('USD') >> Optional.of(SpecFactory.currency)
        then: 'skip adding USD to the repository'
        0 * repository.save({ Currency currency -> currency.currencyCode == 'USD' })
        then: 'ZAR is in the repository'
        1 * repository.findById('ZAR') >> Optional.of(SpecFactory.currency)
        then: 'skip adding ZAR to the repository'
        0 * repository.save({ Currency currency -> currency.currencyCode == 'ZAR' })
        then: 'EUR is in the repository'
        1 * repository.findById('EUR') >> Optional.of(SpecFactory.currency)
        then: 'skip adding ZAR to the repository'
        0 * repository.save({ Currency currency -> currency.currencyCode == 'EUR' })
    }

}
