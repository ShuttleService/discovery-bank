package tech.ioco.discovery.bank.atm

import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory

class AtmServiceSpec extends Specification {
    private AtmRepository repository = Mock()
    private AtmService service = new AtmService(repository: repository)

    def 'add default atm when not already added'() {
        when: 'adding default ATMs'
        service.addDefaults()
        then: 'the atm is not in the repo'
        1 * repository.findByName(Atm.HEAD_OFFICE_ATM_NAME) >> Optional.empty()
        then: 'save the ATM'
        1 * repository.save({ Atm atm -> atm.name == Atm.HEAD_OFFICE_ATM_NAME && atm.location == Atm.HEAD_OFFICE_ATM_LOCATION })
    }

    def 'only add a default atm when not in the repository'() {
        when: 'adding defaults'
        service.addDefaults()
        then: 'the atm is in the repository'
        1 * repository.findByName(Atm.HEAD_OFFICE_ATM_NAME) >> Optional.of(SpecFactory.atm)
        then: 'skip adding the atm'
        0 * repository.save(_)
    }
}
