package tech.ioco.discovery.bank.client.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Issue
import spock.lang.Specification
import tech.ioco.discovery.bank.client.Client
import tech.ioco.discovery.bank.client.ClientRepository

@SpringBootTest
class ClientAccountRepositorySpec extends Specification {
    @Autowired
    private ClientAccountRepository repository
    @Autowired
    private ClientRepository clientRepository

    @Issue('4.2.1. Display transactional accounts with balances. This is the repository test')
    def 'Finding  client accounts orders them by given criteria'() {
        given: 'a client'
        Client client = clientRepository.findByUsername('test').get()
        when: 'finding clients accounts'
        Page<ClientAccount> page = repository.findByClient(client, false, PageRequest.of(0, 3))
        then: 'a page is returned'
        page.totalElements == 1
    }

    @Issue('4.2.3. Withdraw cash')
    def 'withdrawing cash reduces the displayBalance by the withdrawn amount'() {
        given: 'an amount to withdraw'
        BigDecimal withdrawalAmount = 67
        and: 'an account to withdraw from '
        ClientAccount clientAccount = repository.findById('testC').get()
        and: 'the balance before withdrawal'
        BigDecimal balanceBeforeWithdrawal = clientAccount.displayBalance
        when: 'withdrawing from  a client account'
        int numberOfRowsUpdated = repository.withdraw(clientAccount, withdrawalAmount)
        then: 'exactly one row is updated'
        numberOfRowsUpdated == 1
        when: 'getting the client account again'
        clientAccount = repository.findById('testC').get()
        then: 'the display balance had been reduced by the withdrawal amount'
        clientAccount.displayBalance == balanceBeforeWithdrawal - withdrawalAmount
    }

}
