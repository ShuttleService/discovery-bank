package tech.ioco.discovery.bank.atm

import spock.lang.Specification
import spock.lang.Unroll
import tech.ioco.discovery.bank.SpecFactory

import javax.validation.Validation

class DenominationSpec extends Specification {
    def 'A denomination with valid fields is valid'() {
        expect:
        Validation.buildDefaultValidatorFactory().validator.validate(SpecFactory.denomination).empty
    }

    @Unroll('the denomination with value #value, type #type is invalid ')
    def 'A denomination is only valid when it has valid fields'() {
        when: 'validation a denomination'
        boolean valid = Validation.buildDefaultValidatorFactory().validator.validate(new Denomination(value, type)).empty
        then: 'this is invalid'
        !valid
        where:
        value           | type
        null            | null
        BigDecimal.ZERO | null
        null            | SpecFactory.denominationType

    }
}
