package tech.ioco.discovery.bank.currency

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CurrencyConversionRateRepositorySpec extends Specification {
    @Autowired
    private CurrencyConversionRateRepository repository

    def setup() {
        repository.deleteAll()
    }

    def 'currency conversion rates save'() {
        given: 'a currency conversion rate with no persistence identity'
        CurrencyConversionRate conversionRate = new CurrencyConversionRate('TST', 16.67, 'U' as char )
        expect:
        repository.findById(conversionRate.currencyCode).empty
        when: 'saving a conversion rate'
        repository.save(conversionRate)
        then: 'the currency conversion rate is saved'
        repository.findById(conversionRate.currencyCode).present
    }
}
