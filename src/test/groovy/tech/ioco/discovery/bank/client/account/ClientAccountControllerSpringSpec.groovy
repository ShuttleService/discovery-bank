package tech.ioco.discovery.bank.client.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ClientAccountControllerSpringSpec extends Specification {
    @Autowired
    private ClientAccountController controller

    def 'autowire dependencies'() {
        expect:
        controller.repository
        controller.service
    }
}
