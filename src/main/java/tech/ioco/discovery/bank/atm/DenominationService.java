package tech.ioco.discovery.bank.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DenominationService {
    private static final Logger logger = LoggerFactory.getLogger(DenominationService.class);
    @Autowired
    private DenominationRepository repository;
    @Autowired
    private DenominationTypeRepository denominationTypeRepository;


    public void addDefaults() {
        logger.info("Adding denominations, please wait");
        DenominationType denominationType = denominationTypeRepository.findById("N").get();
        Optional<Denomination> denomination = repository.findByValue(BigDecimal.TEN);
        repository.save(new Denomination(BigDecimal.TEN, denominationType));
        denomination = repository.findByValue(BigDecimal.valueOf(20));
        repository.save(new Denomination(BigDecimal.valueOf(20), denominationType));
        denomination = repository.findByValue(BigDecimal.valueOf(50));
        repository.save(new Denomination(BigDecimal.valueOf(50), denominationType));
        denomination = repository.findByValue(BigDecimal.valueOf(100));
        repository.save(new Denomination(BigDecimal.valueOf(100), denominationType));
        denomination = repository.findByValue(BigDecimal.valueOf(200));
        repository.save(new Denomination(BigDecimal.valueOf(200), denominationType));

    }
}
