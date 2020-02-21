package tech.ioco.discovery.bank.atm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class AtmAllocationRepositorySpec extends Specification {
    @Autowired
    private AtmAllocationRepository repository
    @Autowired
    private AtmRepository atmRepository
    @Autowired
    private DenominationRepository denominationRepository

    def 'an atm allocation saves thus the jpa mappings are on point'() {
        given: 'a denomination'
        Denomination denomination = denominationRepository.findByValue(BigDecimal.TEN).get()
        and: 'an atm'
        Atm atm = atmRepository.findByName(Atm.HEAD_OFFICE_ATM_NAME).get()
        and: 'an atm allocation'
        AtmAllocation atmAllocation = new AtmAllocation(atm, denomination, 7)
        expect: 'no persistence identity'
        atmAllocation.atmAllocationId == 0
        when: 'saving an atm allocation'
        atmAllocation = repository.save(atmAllocation)
        then: 'the atm allocation now has persistence identity'
        atmAllocation.atmAllocationId > 0

    }

    def cleanup() {
        repository.deleteAll()
    }
}
