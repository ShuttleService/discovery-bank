package tech.ioco.discovery.bank.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ClientTypeServiceSpringSpec extends Specification {
    @Autowired
    private ClientTypeService service

    def 'autowire dependencies'() {
        expect:
        service.repository
    }
}
