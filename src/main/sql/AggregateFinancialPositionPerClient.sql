select c.title,
       c.name,
       c.surname,
       sum(loan.display_balance)                                                                                                       as loan_aggregate_balance,
       sum(transactional.display_balance)                                                                                              as transactional_balance,
       sum(isnull(loan.display_balance, 0)) + sum(isnull(transactional.display_balance, 0)) + sum(isnull(currency.display_balance, 0)) as net_position

from client c
         outer apply (select inner_loan_ca.display_balance
                      from client_account inner_loan_ca
                               inner join account_type inner_loan_at on inner_loan_ca.account_type_code = inner_loan_at.account_type_code
                      where inner_loan_ca.client_id = c.client_id
                        and inner_loan_at.account_type_code = 'Loan'
) loan

         outer apply (select inner_transactional_ca.display_balance
                      from client_account inner_transactional_ca
                               inner join account_type inner_transactional_at on inner_transactional_ca.account_type_code = inner_transactional_at.account_type_code
                      where inner_transactional_ca.client_id = c.client_id
                        and inner_transactional_at.transactional = 1
) transactional

         outer apply (
    select inner_currency_ca.display_balance * inner_currency_ccr.rate as display_balance
    from client_account inner_currency_ca
             inner join account_type inner_currency_at on inner_currency_ca.account_type_code = inner_currency_at.account_type_code
             inner join currency inner_currency_currency on inner_currency_currency.currency_code = inner_currency_ca.currency_code
             inner join currency_conversion_rate inner_currency_ccr on inner_currency_ccr.currency_code = inner_currency_currency.currency_code
    where inner_currency_ca.client_id = c.client_id
      and inner_currency_at.account_type_code = 'Currency'
) currency

group by c.title, c.name, c.surname