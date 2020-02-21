package tech.ioco.discovery.bank.client.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CreditCardLimitRepositorySpec extends Specification {
    @Autowired
    private CreditCardLimitRepository repository

    def setup() {
        repository.deleteAll()
    }

    def 'a credit card limit saves and is thus the entity is properly configured'() {
        given: 'a Credit Card Limit'
        final String accountNumber = 'account#'
        CreditCardLimit cardLimit = new CreditCardLimit(accountNumber, 34.6)
        expect: 'the credit card limit to have no persistency identity'
        repository.findById(accountNumber).empty
        when: 'saving a credit card limit'
        repository.save(cardLimit)
        then: 'the credit card limit is saved'
        repository.findById(accountNumber).present
    }
}
