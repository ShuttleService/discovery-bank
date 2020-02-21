package tech.ioco.discovery.bank.atm

import spock.lang.Specification
import spock.lang.Unroll
import tech.ioco.discovery.bank.SpecFactory

import javax.validation.Validation

class AtmAllocationSpec extends Specification {

    def 'an atm allocation with all valid fields is valid'() {
        expect:
        Validation.buildDefaultValidatorFactory().validator.validate(SpecFactory.atmAllocation).empty
    }

    @Unroll('An atm allocation with atm #atm, denomination #denomination is invalid')
    def 'a valid atm allocation has all required fields set'() {
        when: 'validating an atm allocation'
        boolean valid = Validation.buildDefaultValidatorFactory().validator.validate(new AtmAllocation(atm, denomination, 3)).empty
        then: 'the atm allocation is invalid'
        !valid
        where:
        atm             | denomination
        null            | null
        SpecFactory.atm | null
        null            | SpecFactory.denomination
    }

}
