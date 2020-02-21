package tech.ioco.discovery.bank.atm

import spock.lang.Specification
import spock.lang.Unroll
import tech.ioco.discovery.bank.SpecFactory

import javax.validation.Validation

class AtmSpec extends Specification {
    def 'an atm with all valid fields is valid'() {
        expect:
        Validation.buildDefaultValidatorFactory().validator.validate(SpecFactory.atm).empty
    }

    @Unroll('an atm is invalid with fields name #name,location #location')
    def 'an atm is valid when all its fields are valid'() {
        when: 'validating an atm'
        boolean valid = Validation.buildDefaultValidatorFactory().validator.validate(new Atm(name, location)).empty
        then: 'its invalid'
        !valid
        where:
        name                      | location
        null                      | null
        'valid'                   | null
        null                      | 'valid'
        ''                        | 'valid'
        'valid'                   | ''
        'more than 10 characters' | 'valid'
    }

}
