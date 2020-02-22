package tech.ioco.discovery.bank.client.account

import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory

class AccountTypeServiceSpec extends Specification {

    private AccountTypeRepository repository = Mock()
    private AccountTypeService service = new AccountTypeService(repository: repository)

    def 'add default account types'() {
        when: 'adding default accounts'
        service.addDefaults()
        then: 'credit card account is not in the repository'
        1 * repository.findById('Savings') >> Optional.empty()
        then: 'add the savings account'
        1 * repository.save({ AccountType type -> type.accountTypeCode == 'Savings' && type.description == 'Savings Account' && type.transactional == true })
        and: 'cheque account is not in the repository'
        1 * repository.findById('Cheque') >> Optional.empty()
        then: 'add the cheque account'
        1 * repository.save({ AccountType type -> type.accountTypeCode == 'Cheque' && type.description == 'Cheque Account' && type.transactional == true })
        and: 'credit card account is not in the repository'
        1 * repository.findById('Credit') >> Optional.empty()
        then: 'add the credit card account'
        1 * repository.save({ AccountType type -> type.accountTypeCode == 'Credit' && type.description == 'Credit Card' && type.transactional == true })
        and: 'currency account is not in the repository'
        1 * repository.findById('Currency') >> Optional.empty()
        then: 'add the currency account'
        1 * repository.save({ AccountType type -> type.accountTypeCode == 'Currency' && type.description == 'Currency Account' && type.transactional == false })
    }

    def 'only add default account types that are not in the repository'() {
        when: 'adding default accounts'
        service.addDefaults()
        then: 'credit card account is in the repository'
        1 * repository.findById('Savings') >> Optional.of(SpecFactory.accountTypeSaving)
        then: 'skip adding the savings account'
        0 * repository.save({ AccountType type -> type.accountTypeCode == 'Savings' })
        and: 'cheque account is in the repository'
        1 * repository.findById('Cheque') >> Optional.of(SpecFactory.accountTypeCheque)
        then: 'skip adding the cheque account'
        0 * repository.save({ AccountType type -> type.accountTypeCode == 'Cheque' })
        and: 'credit card account is in the repository'
        1 * repository.findById('Credit') >> Optional.of(SpecFactory.accountTypeCreditCard)
        then: 'skip adding the credit card account'
        0 * repository.save({ AccountType type -> type.accountTypeCode == 'Credit' })
        and: 'currency  account is in the repository'
        1 * repository.findById('Currency') >> Optional.of(SpecFactory.accountTypeCurrency)
        then: 'skip adding the credit card account'
        0 * repository.save({ AccountType type -> type.accountTypeCode == 'Currency' })

    }

}
