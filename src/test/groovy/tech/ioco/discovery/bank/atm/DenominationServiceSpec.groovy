package tech.ioco.discovery.bank.atm

import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory

class DenominationServiceSpec extends Specification {
    private DenominationRepository repository = Mock()
    private DenominationTypeRepository denominationTypeRepository = Mock()
    private DenominationService service = new DenominationService(repository: repository, denominationTypeRepository: denominationTypeRepository)

    def 'add default denominations'() {
        when: 'adding default denominations'
        service.addDefaults()
        then: 'get the note denomination'
        1 * denominationTypeRepository.findById('N') >> Optional.of(SpecFactory.denominationType)
        then: 'the 10 note is not in the repository'
        1 * repository.findByValue(10) >> Optional.empty()
        then: 'save the 10 note'
        1 * repository.save({ Denomination denomination -> denomination.value == 10 && denomination.denominationType == SpecFactory.denominationType })
        then: 'the 20 note is not in the repository'
        1 * repository.findByValue(20) >> Optional.empty()
        then: 'save the 20 note'
        1 * repository.save({ Denomination denomination -> denomination.value == 20 && denomination.denominationType == SpecFactory.denominationType })
        then: 'the 50 note is not in the repository'
        1 * repository.findByValue(50) >> Optional.empty()
        then: 'save the 50 note'
        1 * repository.save({ Denomination denomination -> denomination.value == 50 && denomination.denominationType == SpecFactory.denominationType })
        then: 'the 100 note is not in the repository'
        1 * repository.findByValue(100) >> Optional.empty()
        then: 'save the 100 note'
        1 * repository.save({ Denomination denomination -> denomination.value == 100 && denomination.denominationType == SpecFactory.denominationType })
        then: 'the 200 note is not in the repository'
        1 * repository.findByValue(200) >> Optional.empty()
        then: 'save the 100 note'
        1 * repository.save({ Denomination denomination -> denomination.value == 200 && denomination.denominationType == SpecFactory.denominationType })
    }
}
