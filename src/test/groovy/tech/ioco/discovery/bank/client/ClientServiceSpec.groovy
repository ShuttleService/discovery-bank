package tech.ioco.discovery.bank.client

import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory

class ClientServiceSpec extends Specification {
    private ClientRepository repository = Mock()
    private PasswordEncoder passwordEncoder = Mock()
    private ClientSubTypeRepository clientSubTypeRepository = Mock()
    private ClientService service = new ClientService(repository: repository, passwordEncoder: passwordEncoder, clientSubTypeRepository: clientSubTypeRepository)

    def 'add test client and admin on start up'() {
        when: 'adding default clients'
        service.addDefaults()
        then: 'the admin user is not in the repo'
        1 * repository.findByUsername('admin') >> Optional.empty()
        and: 'encrypt the admin password'
        1 * passwordEncoder.encode('adminPassword') >> 'admin'
        and: 'get a client sub type'
        1 * clientSubTypeRepository.findById('STII') >> Optional.of(SpecFactory.clientSubType)
        then: 'save the admin user'
        1 * repository.save({ Client client -> client.username == 'admin' && client.password == 'admin' && client.clientSubType == SpecFactory.clientSubType && client.role == Role.ADMIN })
        then: 'a test client is not found'
        1 * repository.findByUsername('test') >> Optional.empty()
        and: 'encrypt the test password'
        1 * passwordEncoder.encode('testPassword') >> 'test'
        then: 'save the test client'
        1 * repository.save({ Client client -> client.username == 'test' && client.password == 'test' && client.clientSubType == SpecFactory.clientSubType && client.role == Role.CLIENT })
    }

    def 'add test client and admin only when there are not in the repo at start up'() {
        when: 'adding default clients'
        service.addDefaults()
        then: 'find a client sub type'
        then: 'the admin user is not in the repo'
        1 * repository.findByUsername('admin') >> Optional.of(SpecFactory.client)
        then: 'skip saving the admin user'
        0 * repository.save({ Client client -> client.username == 'admin' })
        then: 'a test client is not found'
        1 * repository.findByUsername('test') >> Optional.of(SpecFactory.client)
        then: 'skip saving the test client'
        0 * repository.save({ Client client -> client.username == 'test' })
    }
}
