package tech.ioco.discovery.bank

import tech.ioco.discovery.bank.atm.*
import tech.ioco.discovery.bank.client.*
import tech.ioco.discovery.bank.client.account.AccountType
import tech.ioco.discovery.bank.client.account.ClientAccount
import tech.ioco.discovery.bank.client.account.CreditCardLimit
import tech.ioco.discovery.bank.client.account.CurrencyDisplayBalance
import tech.ioco.discovery.bank.currency.Currency
import tech.ioco.discovery.bank.currency.CurrencyConversionRate

class SpecFactory {
    final static DenominationType denominationType = new DenominationType('C', 'some description')
    final static Denomination denomination = new Denomination(BigDecimal.ONE, denominationType)
    final static Denomination denomination200 = new Denomination(200.0, denominationType)
    final static Denomination denomination100 = new Denomination(100.0, denominationType)
    final static Denomination denomination50 = new Denomination(50.0, denominationType)
    final static Denomination denomination20 = new Denomination(20.0, denominationType)
    final static Denomination denomination10 = new Denomination(10.0, denominationType)
    final static WithdrawalAllocation withdrawalAllocation = new WithdrawalAllocation(denomination, 4)
    final static Atm atm = new Atm('atm name', 'some location')
    final static AtmAllocation atmAllocation = new AtmAllocation(atm, denomination, 1)
    final static AtmAllocation atmAllocation200Notes = new AtmAllocation(atm, denomination200, 7)
    final static AtmAllocation atmAllocation100Notes = new AtmAllocation(atm, denomination100, 2)
    final static AtmAllocation atmAllocation50Notes = new AtmAllocation(atm, denomination50, 3)
    final static AtmAllocation atmAllocation20Notes = new AtmAllocation(atm, denomination20, 4)
    final static AtmAllocation atmAllocation10Notes = new AtmAllocation(atm, denomination10, 5)
    final static CreditCardLimit creditCardLimit = new CreditCardLimit('account#', 685)
    final static CurrencyConversionRate currencyConversionRateUS = new CurrencyConversionRate('USD', 14.00, 'D' as char)
    final static CurrencyConversionRate currencyConversionRateEURO = new CurrencyConversionRate('EUR', 17.00, 'U' as char)
    final static Currency currency = new Currency('ABC', 'Some currency', 3)
    final static Currency currencyZAR = new Currency('ZAR', 'Some currency', 2)
    final static Currency currencyUS = new Currency(Currency.CODE_USD, 'Some currency', 2)
    final static Currency currencyEURO = new Currency(Currency.CODE_EURO, 'Some currency', 2)
    final static ClientType clientType = new ClientType('ct', 'some client type')
    final static ClientType clientTypeTransactional = new ClientType('TC', 'some transactional client type')
    final static ClientType clientTypeInvestment = new ClientType('TI', 'some inventment client type')
    final static ClientSubType clientSubType = new ClientSubType('cstc', clientType, 'some client type')
    final static AccountType accountTypeCheque = new AccountType('Cheque', 'Cheque Account', true)
    final static AccountType accountTypeCreditCard = new AccountType('CreditCard', 'Credit Card Account', true)
    final static AccountType accountTypeSaving = new AccountType('Savings', 'Savings Account', true)
    final static AccountType accountTypeCurrency = new AccountType('Currency', 'Currency Account', false)
    final static Client client = new Client('some username', 'password', Title.DR, 'some name', 'some surname', new Date(), clientSubType, Role.CLIENT)
    final static Client otherClient = new Client('some other username', 'password', Title.DR, 'some other name', 'some other surname', new Date(), clientSubType, Role.CLIENT)
    final static ClientAccount clientAccount = new ClientAccount('test', client, accountTypeCheque, currency, BigDecimal.valueOf(1000))
    final static ClientAccount clientAccountForDifferentClient = new ClientAccount('test', otherClient, accountTypeCheque, currency, BigDecimal.ONE)
    final static ClientAccount clientAccountUS = new ClientAccount('test', client, accountTypeCurrency, currencyUS, BigDecimal.ZERO)
    final static ClientAccount clientAccountEURO = new ClientAccount('test', client, accountTypeCurrency, currencyEURO, BigDecimal.TEN)
    final static CurrencyDisplayBalance currencyDisplayBalance = new CurrencyDisplayBalance(currency, BigDecimal.ONE, BigDecimal.TEN)
    final static WithdrawalRequest withdrawalRequest = new WithdrawalRequest('atm', SpecFactory.client, 'clientAccount#', BigDecimal.TEN)
}
