package tech.ioco.discovery.bank.atm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

@SpringBootTest
class AtmAllocationRepositorySpec extends Specification {
    @Autowired
    private AtmAllocationRepository repository
    @Autowired
    private AtmRepository atmRepository
    @Autowired
    private DenominationRepository denominationRepository
    @Autowired
    private DenominationTypeRepository denominationTypeRepository

    def cleanup() {
        repository.deleteAll()
    }


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

    def 'get allocations by an atm '() {
        given: 'note and coin denominations for different atms'
        Denomination denomination = denominationRepository.findByValue(100).get()
        DenominationType coin = denominationTypeRepository.findById("C").get()
        Denomination coinDenomination = denominationRepository.save(new Denomination(5.0, coin))
        and: 'an atm '
        Atm atm = atmRepository.findByName(Atm.HEAD_OFFICE_ATM_NAME).get()
        and: 'another atm'
        Atm another = atmRepository.save(new Atm('test', 'here'))
        and: 'allocations for the atms'
        repository.save(new AtmAllocation(atm, denomination, 58))
        repository.save(new AtmAllocation(atm, coinDenomination, 5))
        repository.save(new AtmAllocation(another, denomination, 5))
        repository.save(new AtmAllocation(another, coinDenomination, 53))
        when: 'getting the atm allocations'
        Page<AtmAllocation> allocationPage = repository.findByAtm(another, PageRequest.of(0, 5))
        then: 'all the allocations belong to this atm'
        allocationPage.content.stream().allMatch({
            AtmAllocation allocation ->
                allocation.atm.name == another.name &&
                        allocation.denomination.denominationType.denominationTypeCode == 'N'
        })
    }
}
