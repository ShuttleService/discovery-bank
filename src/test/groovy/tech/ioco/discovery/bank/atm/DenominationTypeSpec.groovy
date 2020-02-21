package tech.ioco.discovery.bank.atm

import spock.lang.Specification
import spock.lang.Unroll
import tech.ioco.discovery.bank.SpecFactory

import javax.validation.Validation

class DenominationTypeSpec extends Specification {

    def 'a denomination type with both valid fields is valid'() {
        expect:
        Validation.buildDefaultValidatorFactory().validator.validate(SpecFactory.denominationType).empty
    }

    @Unroll('A denomination type with code #code, description is invalid ')
    def 'A denomination type is valid when both fields are valid'() {
        when: 'validating denomination types'
        boolean valid = Validation.buildDefaultValidatorFactory().validator.validate(new DenominationType(code, description)).empty
        then: 'this is invalid'
        !valid
        where:
        code    | description
        null    | null
        'valid' | null
        null    | 'valid'
        'va'    | 'valid'
        ''      | 'valid'
        'v'     | ''
        'v'     | null
    }
}
