package tech.ioco.discovery.bank

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import tech.ioco.discovery.bank.client.ClientService

@SpringBootTest
class ClientServiceSpringSpec extends Specification {
    @Autowired
    private ClientService service

    def 'autowire dependencies'() {
        expect:
        service.repository
    }
}
