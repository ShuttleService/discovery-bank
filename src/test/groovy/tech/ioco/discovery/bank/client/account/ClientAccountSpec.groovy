package tech.ioco.discovery.bank.client.account

import spock.lang.Specification
import spock.lang.Unroll
import tech.ioco.discovery.bank.SpecFactory
import javax.validation.Validation

class ClientAccountSpec extends Specification {
    def 'a client account with all fields is valid'() {
        expect:
        Validation.buildDefaultValidatorFactory().validator.validate(SpecFactory.client).empty
    }

    @Unroll("a client account with client #client, account type #accountType, currency #currency and balance #balance is invalid")
    def 'a client account with at least one invalid field is invalid'() {
        when: 'validating a client account'
        boolean valid = Validation.buildDefaultValidatorFactory().validator.validate(new ClientAccount('client', client, accountType, currency, balance)).empty
        then: 'the client is invalid'
        !valid
        where:
        client             | accountType             | currency             | balance
        null               | null                    | null                 | null
        null               | SpecFactory.accountType | SpecFactory.currency | BigDecimal.ONE
        SpecFactory.client | null                    | SpecFactory.currency | BigDecimal.ONE
        SpecFactory.client | SpecFactory.accountType | null                 | BigDecimal.ONE
        SpecFactory.client | SpecFactory.accountType | SpecFactory.currency | null
    }
}