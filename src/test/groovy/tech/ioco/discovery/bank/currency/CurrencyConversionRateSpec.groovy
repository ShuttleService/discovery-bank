package tech.ioco.discovery.bank.currency

import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory

import javax.validation.Validation

class CurrencyConversionRateSpec extends Specification {
    def 'a currency conversion rate with correct fields is valid'() {
        expect:
        Validation.buildDefaultValidatorFactory().validator.validate(SpecFactory.currencyConversionRateEURO).empty
    }

    def 'a currency conversion rate with null rate or currency code not 3 characters is invalid'() {
        when: 'checking validity'
        boolean valid = Validation.buildDefaultValidatorFactory().validator.validate(new CurrencyConversionRate(code, rate, 'D' as char)).empty
        then: 'this is invalid'
        !valid
        where:
        code   | rate
        'ZAR'  | null
        'Z'    | 56
        'ZA'   | 56
        ''     | 56
        'ZARD' | 56
    }
}
