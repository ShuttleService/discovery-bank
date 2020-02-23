package tech.ioco.discovery.bank.atm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class AtmAllocationServiceSpringSpec extends Specification {
    @Autowired
    private AtmAllocationService service

    def 'autowire dependencies'() {
        expect:
        service.repository
        service.clientRepository
        service.atmRepository
        service.clientAccountRepository
    }
}
