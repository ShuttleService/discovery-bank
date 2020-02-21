package tech.ioco.discovery.bank.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DenominationTypeService {
    private static final Logger logger = LoggerFactory.getLogger(DenominationTypeService.class);
    @Autowired
    private DenominationTypeRepository repository;

    public void addDefaults() {
        logger.info("Adding denomination types");
        Optional<DenominationType> denominationType = repository.findById("N");
        if (denominationType.isEmpty()) {
            logger.info("Adding denomination type Bank Note");
            repository.save(new DenominationType("N", "Bank Note"));
        }
        denominationType = repository.findById("C");
        if (denominationType.isEmpty()) {
            logger.info("Adding denomination type bank coin");
            repository.save(new DenominationType("C", "Coin"));
        }
    }
}
