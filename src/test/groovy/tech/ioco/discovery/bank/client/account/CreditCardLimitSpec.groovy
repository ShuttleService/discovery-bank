package tech.ioco.discovery.bank.client.account

import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory

import javax.validation.Validation

class CreditCardLimitSpec extends Specification {
    def 'a credit card limit with a valid client account number and account limit is valid'() {
        expect:
        Validation.buildDefaultValidatorFactory().validator.validate(SpecFactory.creditCardLimit).empty
    }

    def 'A credit card limit with a null account limit is invalid'() {
        expect:
        !Validation.buildDefaultValidatorFactory().validator.validate(new CreditCardLimit('account#', null)).empty
    }
}
