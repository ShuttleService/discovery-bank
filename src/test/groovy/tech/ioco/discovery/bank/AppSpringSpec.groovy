package tech.ioco.discovery.bank

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import tech.ioco.discovery.bank.client.account.ClientAccountRepository

@SpringBootTest
class AppSpringSpec extends Specification {
    @Autowired
    private ClientAccountRepository clientAccountRepository

    def 'init runs on start up'() {
        expect:
        clientAccountRepository.findById('test').present
    }
}
