package tech.ioco.discovery.bank.atm

import spock.lang.Specification
import tech.ioco.discovery.bank.SpecFactory

class DenominationTypeServiceSpec extends Specification {

    private DenominationTypeRepository repository = Mock()
    private DenominationTypeService service = new DenominationTypeService(repository: repository)

    def 'Add default denomination types'() {
        when: 'adding defaults'
        service.addDefaults()
        then: 'the notes are not in the repository'
        1 * repository.findById('N') >> Optional.empty()
        then: 'add the note'
        1 * repository.save({ DenominationType type -> type.denominationTypeCode == 'N' && type.description == 'Bank Note' })
        and: 'the coin is not in the repository'
        1 * repository.findById('C') >> Optional.empty()
        then: 'add the coin deniomination type'
        1 * repository.save({ DenominationType type -> type.denominationTypeCode == 'C' && type.description == 'Coin' })
    }

    def 'only Add default denomination types that are not in the repository'() {
        when: 'adding defaults'
        service.addDefaults()
        then: 'the notes are in the repository'
        1 * repository.findById('N') >> Optional.of(SpecFactory.denominationType)
        then: 'skip adding the note'
        0 * repository.save({ DenominationType type -> type.denominationTypeCode == 'N' && type.description == 'Bank Note' })
        and: 'the coin is in the repository'
        1 * repository.findById('C') >> Optional.of(SpecFactory.denominationType)
        then: 'add the coin deniomination type'
        0 * repository.save({ DenominationType type -> type.denominationTypeCode == 'C' && type.description == 'Coin' })
    }
}
