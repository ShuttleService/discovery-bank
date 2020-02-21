package tech.ioco.discovery.bank.currency

import spock.lang.Specification
import spock.lang.Unroll
import tech.ioco.discovery.bank.SpecFactory

import javax.validation.Validation

class CurrencySpec extends Specification {

    def 'a currency with valid fields is valid'() {
        expect:
        Validation.buildDefaultValidatorFactory().validator.validate(SpecFactory.currency).empty
    }

    @Unroll("A currency with invalid code #code, desccription #description is invalid")
    def 'A currency is only valid when all its fields are valid'() {
        when: 'validating a currency'
        boolean valid = Validation.buildDefaultValidatorFactory().validator.validate(new Currency(code, description, 2)).empty
        then: 'the currency is invalid'
        !valid
        where:
        code  | description
        null  | null
        ''    | 'VALID'
        'A'   | 'VALID'
        'AB'  | 'VALID'
        'ABC' | ''
        'ABC' | null

    }
}

