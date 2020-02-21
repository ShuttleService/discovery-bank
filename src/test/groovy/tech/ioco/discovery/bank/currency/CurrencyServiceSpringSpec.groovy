package tech.ioco.discovery.bank.currency

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CurrencyServiceSpringSpec extends Specification {
    @Autowired
    private CurrencyService service

    def 'autowire dependencies'() {
        expect:
        service.repository
    }
}
