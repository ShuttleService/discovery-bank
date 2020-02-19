package tech.ioco.discovery.bank.client

import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory

class ClientSubTypeServiceSpec extends Specification {
    private ClientSubTypeRepository repository = Mock()
    private ClientTypeRepository clientTypeRepository = Mock()
    private ClientSubTypeService service = new ClientSubTypeService(repository: repository, clientTypeRepository: clientTypeRepository)

    def 'add default client sub types should they not exist'() {
        when: 'adding default client sub types '
        service.addDefaults()
        then: 'get the Investment and Transactional Account types'
        1 * clientTypeRepository.findById('IC') >> Optional.of(SpecFactory.clientTypeInvestment)
        1 * clientTypeRepository.findById('TC') >> Optional.of(SpecFactory.clientTypeTransactional)
        then: 'client sub type individual investment is not in the repository'
        1 * repository.findById('STII') >> Optional.empty()
        then: 'add the client sub type'
        1 * repository.save({ ClientSubType type -> type.clientSubTypeCode == 'STII' && type.clientType == SpecFactory.clientTypeInvestment && type.description == 'This is an individual investment client sub type' })
        then: 'client sub type individual transaction is not in the repository'
        1 * repository.findById('STIT') >> Optional.empty()
        then: 'add the client sub type'
        1 * repository.save({ ClientSubType type -> type.clientSubTypeCode == 'STIT' && type.clientType.clientTypeCode == SpecFactory.clientTypeTransactional.clientTypeCode && type.description == 'This is an individual transactional client sub type' })
        and: 'client sub type company investment is not in the repository'
        1 * repository.findById('STCI') >> Optional.empty()
        then: 'add the company account '
        1 * repository.save({ ClientSubType type -> type.clientSubTypeCode == 'STCI' && type.clientType == SpecFactory.clientTypeInvestment && type.description == 'This is a company investment client sub type' })
        and: 'client sub type company transactional is not in the repository'
        1 * repository.findById('STCT') >> Optional.empty()
        then: 'add the company account '
        1 * repository.save({ ClientSubType type -> type.clientSubTypeCode == 'STCT' && type.clientType == SpecFactory.clientTypeTransactional && type.description == 'This is a company transactional client sub type' })
    }

    def 'skip adding a default client should they it exist'() {
        when: 'adding default client types '
        service.addDefaults()
        then: 'get the Investment and Transactional Account types'
        1 * clientTypeRepository.findById('IC') >> Optional.of(SpecFactory.clientTypeInvestment)
        1 * clientTypeRepository.findById('TC') >> Optional.of(SpecFactory.clientTypeTransactional)
        then: 'client sub type individual investment is in the repository'
        1 * repository.findById('STII') >> Optional.of(SpecFactory.clientSubType)
        and: 'skip adding the individual investment client type'
        0 * repository.save({ ClientSubType clientType -> clientType.clientSubTypeCode == 'STII' })
        and: 'client type individual transactional in the repository'
        1 * repository.findById('STIT') >> Optional.of(SpecFactory.clientSubType)
        then: 'skip adding the individual transactional client type'
        0 * repository.save({ ClientSubType clientType -> clientType.clientSubTypeCode == 'STIT' })
        and: 'client sub type company investment is in the repository'
        1 * repository.findById('STCI') >> Optional.of(SpecFactory.clientSubType)
        and: 'skip adding the company investment client type'
        0 * repository.save({ ClientSubType clientType -> clientType.clientSubTypeCode == 'STCI' })
        and: 'client type company transactional in the repository'
        1 * repository.findById('STCT') >> Optional.of(SpecFactory.clientSubType)
        then: 'skip adding the company transactional client type'
        0 * repository.save({ ClientSubType clientType -> clientType.clientSubTypeCode == 'STCT' })
    }


}
