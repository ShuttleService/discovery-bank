package tech.ioco.discovery.bank.atm

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import spock.lang.Issue
import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory
import tech.ioco.discovery.bank.client.Client
import tech.ioco.discovery.bank.client.ClientRepository
import tech.ioco.discovery.bank.client.account.ClientAccountRepository

class AtmAllocationServiceSpec extends Specification {
    private AtmAllocationRepository repository = Mock()
    private AtmRepository atmRepository = Mock()
    private ClientAccountRepository clientAccountRepository = Mock()
    private ClientRepository clientRepository = Mock()
    private AtmAllocationService service = new AtmAllocationService(repository: repository, atmRepository: atmRepository, clientAccountRepository: clientAccountRepository,
            clientRepository: clientRepository)

    def setup() {
        SpecFactory.otherClient.clientId = 1
    }

    @Issue('4.2.3. Withdraw cash')
    def 'withdrawing an amount returns withdrawal allocations for each denomination'() {
        given: 'an atm name'
        final String atmName = 'atmName'
        and: 'a client'
        Client client = SpecFactory.client
        and: 'a client account number'
        final String clientAccountNumber = SpecFactory.clientAccount.clientAccountNumber
        and: 'an amount to withdraw'
        BigDecimal amountToWithdraw = 380
        when: 'performing a withdrawal '
        List<WithdrawalAllocation> withdrawalAllocations = service.withdraw(atmName, client, clientAccountNumber, amountToWithdraw)
        then: 'get the client from the repository'
        1 * clientRepository.findById(client.clientId) >> Optional.of(client)
        then: 'find the client account'
        1 * clientAccountRepository.findById(clientAccountNumber) >> Optional.of(SpecFactory.clientAccount)
        then: 'get the atm from the repository'
        1 * atmRepository.findByName(atmName) >> Optional.of(SpecFactory.atm)
        then: 'get the atm allocation order by denomination value'
        1 * repository.findByAtm(SpecFactory.atm,
                { Pageable pageable ->
                    pageable.sort.stream().allMatch({
                        Sort.Order order -> order.descending && order.property == 'denomination.value'
                    }) && pageable.pageNumber == 0 && pageable.pageSize == 50
                }) >> new PageImpl<>([SpecFactory.atmAllocation200Notes, SpecFactory.atmAllocation100Notes, SpecFactory.atmAllocation50Notes,
                                      SpecFactory.atmAllocation20Notes, SpecFactory.atmAllocation10Notes
        ])
        then: 'withdraw the amount'
        1 * clientAccountRepository.withdraw(SpecFactory.clientAccount, amountToWithdraw)
        then: '5 Withdrawal allocations are returned'
        withdrawalAllocations.size() == 5
        and: 'there is exactly one R200 allocation'
        withdrawalAllocations.stream().filter({
            WithdrawalAllocation withdrawalAllocation -> withdrawalAllocation.denomination.value == 200 && withdrawalAllocation.count == 1
        }).count() == 1
        and: 'there is exactly one R100 allocation'
        withdrawalAllocations.stream().filter({
            WithdrawalAllocation withdrawalAllocation -> withdrawalAllocation.denomination.value == 100 && withdrawalAllocation.count == 1
        }).count() == 1
        and: 'there is exactly one R50 allocation'
        withdrawalAllocations.stream().filter({
            WithdrawalAllocation withdrawalAllocation -> withdrawalAllocation.denomination.value == 50 && withdrawalAllocation.count == 1
        }).count() == 1
        and: 'there is exactly one R20 allocation'
        withdrawalAllocations.stream().filter({
            WithdrawalAllocation withdrawalAllocation -> withdrawalAllocation.denomination.value == 20 && withdrawalAllocation.count == 1
        }).count() == 1
        and: 'there is exactly one R10 allocation'
        withdrawalAllocations.stream().filter({
            WithdrawalAllocation withdrawalAllocation -> withdrawalAllocation.denomination.value == 10 && withdrawalAllocation.count == 1
        }).count() == 1
    }

    def 'only add non zero withdrawal allocations skipping denominations not assignable to the allocation'() {
        when: 'making a withdrawing'
        List<WithdrawalAllocation> withdrawalAllocations = service.withdraw('some atm name', SpecFactory.client, SpecFactory.clientAccount.clientAccountNumber, 70)
        then: 'the client is found'
        1 * clientRepository.findById(_) >> Optional.of(SpecFactory.client)
        then: 'the client account is found'
        1 * clientAccountRepository.findById(_) >> Optional.of(SpecFactory.clientAccount)
        and: 'find the atm '
        1 * atmRepository.findByName(_) >> Optional.of(SpecFactory.atm)
        and: 'get the atm allocations'
        1 * repository.findByAtm(_, _) >> new PageImpl<>([SpecFactory.atmAllocation200Notes, SpecFactory.atmAllocation100Notes, SpecFactory.atmAllocation50Notes, SpecFactory.atmAllocation10Notes])
        then: 'withdraw the amount'
        1 * clientAccountRepository.withdraw(_, _)
        then: 'only two allocations are returned'
        withdrawalAllocations.size() == 2
        and: 'the 50 rand note allocation with 1 note'
        withdrawalAllocations.stream().filter(
                { WithdrawalAllocation withdrawalAllocation -> withdrawalAllocation.denomination.value == 50 && withdrawalAllocation.count == 1 }).count() == 1
        and: 'there 10 rand allocation with 2 notes'
        withdrawalAllocations.stream().filter(
                { WithdrawalAllocation withdrawalAllocation -> withdrawalAllocation.denomination.value == 10 && withdrawalAllocation.count == 2 }).count() == 1
    }

    def 'Abort withdrawal with error “ATM not registered or unfunded” for an atm that is not found '() {
        when: 'making a withdrawal'
        service.withdraw('atm name', SpecFactory.client, SpecFactory.clientAccount.clientAccountNumber, 10)
        then: 'find the client'
        1 * clientRepository.findById(_) >> Optional.of(SpecFactory.client)
        then: 'find the client account'
        1 * clientAccountRepository.findById(_) >> Optional.of(SpecFactory.clientAccount)
        then: 'fail to find the atm'
        1 * atmRepository.findByName(_) >> Optional.empty()
        and: 'no bother to find the atm allocations'
        0 * repository.findByAtm(_, _)
        and: 'no withdrawal happens'
        0 * clientAccountRepository.withdraw(_, _)
        and: 'an exception is thrown'
        RuntimeException exception = thrown(RuntimeException)
        and: 'the message says ATM not registered or unfunded'
        exception.message == 'ATM not registered or unfunded'
    }

    def 'Abort withdrawal with error “ATM not registered or unfunded” for an atm that with no allocations not found '() {
        when: 'making a withdrawal'
        service.withdraw('atm name', SpecFactory.client, SpecFactory.clientAccount.clientAccountNumber, 10)
        then: 'find the client'
        1 * clientRepository.findById(_) >> Optional.of(SpecFactory.client)
        then: 'find the client account'
        1 * clientAccountRepository.findById(_) >> Optional.of(SpecFactory.clientAccount)
        then: 'find the atm'
        1 * atmRepository.findByName(_) >> Optional.of(SpecFactory.atm)
        and: 'the atm has zero allocations'
        1 * repository.findByAtm(_, _) >> new PageImpl<>([])
        and: 'no withdrawal happens'
        0 * clientAccountRepository.withdraw(_, _)
        and: 'an exception is thrown'
        RuntimeException exception = thrown(RuntimeException)
        and: 'the message says ATM not registered or unfunded'
        exception.message == 'ATM not registered or unfunded'
    }

    def 'Abort withdrawal with error “Insufficient funds” when client attempts to withdraw an amount more than their balance'() {
        when: 'making a withdrawal'
        service.withdraw('atm name', SpecFactory.client, SpecFactory.clientAccount.clientAccountNumber, SpecFactory.clientAccount.displayBalance + 1)
        then: 'find the client'
        1 * clientRepository.findById(_) >> Optional.of(SpecFactory.client)
        then: 'find the client account'
        1 * clientAccountRepository.findById(_) >> Optional.of(SpecFactory.clientAccount)
        then: 'no bother to find the atm'
        0 * atmRepository.findByName(_)
        and: 'no bother to find atm allocations'
        0 * repository.findByAtm(_, _)
        and: 'no withdrawal happens'
        0 * clientAccountRepository.withdraw(_, _)
        and: 'an exception is thrown'
        RuntimeException exception = thrown(RuntimeException)
        and: 'Insufficient funds'
        exception.message == 'Insufficient funds'
    }

    def 'Abort withdrawal should the atm have less allocations than what the client wants to withdraw with message "Amount not available, would you like to draw X"'() {
        when: 'making a withdrawal'
        service.withdraw('atm name', SpecFactory.client, SpecFactory.clientAccount.clientAccountNumber, SpecFactory.clientAccount.displayBalance)
        then: 'find the client'
        1 * clientRepository.findById(_) >> Optional.of(SpecFactory.client)
        then: 'find the client account'
        1 * clientAccountRepository.findById(_) >> Optional.of(SpecFactory.clientAccount)
        then: 'find the atm'
        1 * atmRepository.findByName(_) >> Optional.of(SpecFactory.atm)
        and: 'find atm allocations with an amount short of what the client would like to withdraw'
        1 * repository.findByAtm(_, _) >> new PageImpl<>([SpecFactory.atmAllocation10Notes])
        and: 'no withdrawal happens'
        0 * clientAccountRepository.withdraw(_, _)
        and: 'an exception is thrown'
        RuntimeException exception = thrown(RuntimeException)
        and: 'Amount not available, would you like to draw X'
        exception.message == "Amount not available, would you like to draw R50,00"
    }

    def 'return empty withdrawal allocation for a non transactional account. Aborting withdrawal'() {
        when: 'making a withdrawal'
        List<WithdrawalAllocation> withdrawalAllocations = service.withdraw('atm name', SpecFactory.client, SpecFactory.clientAccount.clientAccountNumber, 10)
        then: 'find the client'
        1 * clientRepository.findById(_) >> Optional.of(SpecFactory.client)
        then: 'find the client non transactional account account'
        1 * clientAccountRepository.findById(_) >> Optional.of(SpecFactory.clientAccountUS)
        then: 'no bother to find the atm'
        0 * atmRepository.findByName(_)
        and: 'no bother to find the atm allocations'
        0 * repository.findByAtm(_, _)
        and: 'no withdrawal happens'
        0 * clientAccountRepository.withdraw(_, _)
        and: 'an empty page list is returned'
        withdrawalAllocations.empty
    }

    def 'return empty withdrawal allocation for a non existent client. Aborting withdrawal'() {
        when: 'making a withdrawal'
        List<WithdrawalAllocation> withdrawalAllocations = service.withdraw('atm name', SpecFactory.client, SpecFactory.clientAccount.clientAccountNumber, 10)
        then: 'the client is not found'
        1 * clientRepository.findById(_) >> Optional.empty()
        then: 'no bother to find the client account'
        0 * clientAccountRepository.findById(_)
        then: 'no bother to find the atm'
        0 * atmRepository.findByName(_)
        and: 'no bother to find the atm allocations'
        0 * repository.findByAtm(_, _)
        and: 'no withdrawal happens'
        0 * clientAccountRepository.withdraw(_, _)
        and: 'an empty page list is returned'
        withdrawalAllocations.empty
    }

    def 'return empty withdrawal allocation for a non existent client account. Abort the withdrawal'() {
        when: 'making a withdrawal'
        List<WithdrawalAllocation> withdrawalAllocations = service.withdraw('atm name', SpecFactory.client, SpecFactory.clientAccount.clientAccountNumber, 10)
        then: 'the client is  found'
        1 * clientRepository.findById(_) >> Optional.of(SpecFactory.client)
        then: 'the client account is not found'
        1 * clientAccountRepository.findById(_) >> Optional.empty()
        then: 'no bother to find the atm'
        0 * atmRepository.findByName(_)
        and: 'no bother to find the atm allocations'
        0 * repository.findByAtm(_, _)
        and: 'no withdrawal happens'
        0 * clientAccountRepository.withdraw(_, _)
        and: 'an empty page list is returned'
        withdrawalAllocations.empty
    }

    def 'return empty withdrawal allocation and abort the withdrawal if the logged in client does not own the account to withdraw from'() {
        when: 'making a withdrawal with from an account that belongs to a user other than the logged in client'
        List<WithdrawalAllocation> withdrawalAllocations = service.withdraw('atm name', SpecFactory.client, 'account#', 10)
        then: 'the client is found'
        1 * clientRepository.findById(_) >> Optional.of(SpecFactory.client)
        then: 'an account is found not belonging to this client'
        1 * clientAccountRepository.findById(_) >> Optional.of(SpecFactory.clientAccountForDifferentClient)
        then: 'no bother to find the atm'
        0 * atmRepository.findByName(_)
        and: 'no bother to find the atm allocations'
        0 * repository.findByAtm(_, _)
        and: 'no withdrawal happens'
        0 * clientAccountRepository.withdraw(_, _)
        and: 'an empty page list is returned'
        withdrawalAllocations.empty
    }

}
