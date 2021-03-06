select c.client_id, c.surname,ca.client_account_number,act.description,ca.display_balance
from client_account ca
inner join client c on ca.client_id = c.client_id
inner join account_type act on act.account_type_code = ca.account_type_code
where act.transactional = 1 and
ca.display_balance = ( select max(innerca.display_balance)
                             from client_account innerca
                             where innerca.client_id = c.client_id
)