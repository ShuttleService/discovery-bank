package tech.ioco.discovery.bank.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate


@SpringBootTest
class ClientRepositorySpec extends Specification {
    @Autowired
    private ClientRepository repository
    @Autowired
    private ClientSubTypeRepository clientSubTypeRepository

    def setup() {
        clientSubTypeRepository.deleteAll()
    }

    def 'A client successfully saves thus the jpa mappings are on point'() {
        given: 'a client sub type'
        ClientSubType clientSubType = clientSubTypeRepository.save(new ClientSubType('CODE', 'CO', 'some description'))
        and: 'a client with no persistence identity'
        Client client = new Client('some user name', 'some password', Title.DR, 'some name', 'Some surname', LocalDate.now(), clientSubType)
        expect:
        client.clientId == 0
        when: 'saving the client'
        client = repository.save(client)
        then: 'the client now has persistence identity, thus saved'
        client.clientId > 0
    }
}
