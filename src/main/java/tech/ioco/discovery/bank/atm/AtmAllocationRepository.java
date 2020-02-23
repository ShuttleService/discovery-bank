package tech.ioco.discovery.bank.atm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AtmAllocationRepository extends JpaRepository<AtmAllocation, Integer> {
    @Query("select aa " +
            "from AtmAllocation aa " +
            "where aa.atm = :atm and aa.denomination.denominationType.denominationTypeCode = 'N'")
    Page<AtmAllocation> findByAtm(@Param("atm") Atm atm, Pageable pageable);
}
