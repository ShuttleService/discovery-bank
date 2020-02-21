package tech.ioco.discovery.bank.client.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class AccountTypeServiceSpringSpec extends Specification {

    @Autowired
    private AccountTypeService service

    def 'autowires dependencies'() {
        expect:
        service.repository
    }
}
