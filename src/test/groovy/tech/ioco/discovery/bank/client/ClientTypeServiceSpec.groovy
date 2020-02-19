package tech.ioco.discovery.bank.client

import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory

class ClientTypeServiceSpec extends Specification {
    private ClientTypeRepository repository = Mock()
    private ClientTypeService service = new ClientTypeService(repository: repository)

    def 'add default client types should they not exist'() {
        when: 'adding default client types '
        service.addDefaults()
        then: 'client type investment is not in the repository'
        1 * repository.findById('IC') >> Optional.empty()
        then: 'add the client type'
        1 * repository.save({ ClientType type -> type.clientTypeCode == 'IC' && type.description == 'This is an investment client' })
        and: 'client type transactional is not in the repository'
        1 * repository.findById('TC') >> Optional.empty()
        then: 'add the individual client type'
        1 * repository.save({ ClientType type -> type.clientTypeCode == 'TC' && type.description == 'This is a transactional client' })
    }

    def 'skip adding a default client should they it exist'() {
        when: 'adding default client types '
        service.addDefaults()
        then: 'client type Investment is  in the repository'
        1 * repository.findById('IC') >> Optional.of(SpecFactory.clientType)
        then: 'skip adding the investment client type'
        0 * repository.save({ ClientType clientType -> clientType.clientTypeCode == 'IC' })
        and: 'client type transactional in the repository'
        1 * repository.findById('TC') >> Optional.of(SpecFactory.clientType)
        then: 'skip adding the transactional client type'
        0 * repository.save({ ClientType clientType -> clientType.clientTypeCode == 'IC' })
    }


}
