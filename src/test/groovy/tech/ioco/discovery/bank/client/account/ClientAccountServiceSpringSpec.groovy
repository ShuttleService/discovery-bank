package tech.ioco.discovery.bank.client.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ClientAccountServiceSpringSpec extends Specification {
    @Autowired
    private ClientAccountService service

    def 'autowire dependencies'() {
        expect:
        service.repository
        service.clientRepository
        service.accountTypeRepository
        service.creditCardLimitRepository
        service.currencyRepository
    }
}
