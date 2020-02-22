package tech.ioco.discovery.bank


import tech.ioco.discovery.bank.atm.Atm
import tech.ioco.discovery.bank.atm.AtmAllocation
import tech.ioco.discovery.bank.atm.Denomination
import tech.ioco.discovery.bank.atm.DenominationType
import tech.ioco.discovery.bank.client.Client
import tech.ioco.discovery.bank.client.ClientSubType
import tech.ioco.discovery.bank.client.ClientType
import tech.ioco.discovery.bank.client.Role
import tech.ioco.discovery.bank.client.Title
import tech.ioco.discovery.bank.client.account.AccountType
import tech.ioco.discovery.bank.client.account.ClientAccount
import tech.ioco.discovery.bank.client.account.CreditCardLimit
import tech.ioco.discovery.bank.client.account.CurrencyDisplayBalance
import tech.ioco.discovery.bank.currency.Currency
import tech.ioco.discovery.bank.currency.CurrencyConversionRate

import java.time.LocalDate

class SpecFactory {
    final static DenominationType denominationType = new DenominationType('C', 'some description')
    final static Denomination denomination = new Denomination(BigDecimal.ONE, denominationType)
    final static Atm atm = new Atm('atm name', 'some location')
    final static AtmAllocation atmAllocation = new AtmAllocation(atm, denomination, 1)
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
    final static Client client = new Client('some username', 'password', Title.DR, 'some name', 'some surname', LocalDate.now(), clientSubType, Role.ADMIN)
    final static ClientAccount clientAccount = new ClientAccount('test', client, accountTypeCheque, currency, BigDecimal.ONE)
    final static ClientAccount clientAccountUS = new ClientAccount('test', client, accountTypeCurrency, currencyUS, BigDecimal.ZERO)
    final static ClientAccount clientAccountEURO = new ClientAccount('test', client, accountTypeCurrency, currencyEURO, BigDecimal.TEN)
    final static CurrencyDisplayBalance currencyDisplayBalance = new CurrencyDisplayBalance(currency, BigDecimal.ONE, BigDecimal.TEN)
}
