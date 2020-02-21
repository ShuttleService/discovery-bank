package tech.ioco.discovery.bank.client.account

import spock.lang.Specification
import spock.lang.Unroll
import tech.ioco.discovery.bank.SpecFactory

import javax.validation.Validation

class AccountTypeSpec extends Specification {

    def 'an account type with valid values is valid'() {
        expect:
        Validation.buildDefaultValidatorFactory().validator.validate(SpecFactory.accountType).empty
    }

    @Unroll('An account type with invalid account type code #accountTypeCode, description #description will be invalid')
    def 'An account type is valid when all fields are valid'() {
        when: 'validating an account'
        boolean invalid = Validation.buildDefaultValidatorFactory().validator.validate(new AccountType(accountTypeCode, description, true)).empty
        then: 'the account type is invalid'
        !invalid
        where:
        accountTypeCode           | description
        null                      | null
        ''                        | ''
        null                      | 'valid'
        'valid'                   | null
        ''                        | 'valid'
        'valid'                   | ''
        'more than 10 characters' | 'valid'
    }
}
