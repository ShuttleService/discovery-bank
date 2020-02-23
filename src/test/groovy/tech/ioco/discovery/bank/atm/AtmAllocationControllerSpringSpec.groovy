package tech.ioco.discovery.bank.atm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class AtmAllocationControllerSpringSpec extends Specification {

    @Autowired
    private AtmAllocationController controller

    def 'autowire dependencies'() {
        expect:
        controller.service
    }
}
