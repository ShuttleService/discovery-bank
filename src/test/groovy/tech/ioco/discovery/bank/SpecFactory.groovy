package tech.ioco.discovery.bank

import tech.ioco.discovery.bank.client.ClientSubType
import tech.ioco.discovery.bank.client.ClientType

class SpecFactory {
    final static ClientType clientType = new ClientType('ct', 'some client type')
    final static ClientType clientTypeTransactional = new ClientType('TC', 'some transactional client type')
    final static ClientType clientTypeInvestment = new ClientType('TI', 'some inventment client type')
    final static ClientSubType clientSubType = new ClientSubType('cstc', clientType, 'some client type')
}
