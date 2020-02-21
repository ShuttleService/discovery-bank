package tech.ioco.discovery.bank.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AtmService {
    private static final Logger logger = LoggerFactory.getLogger(AtmService.class);
    @Autowired
    private AtmRepository repository;

    public void addDefaults() {
        logger.info("Adding default Atms");
        Optional<Atm> atm = repository.findByName(Atm.HEAD_OFFICE_ATM_NAME);
        if (atm.isEmpty()) {
            logger.info("Adding {} atm", Atm.HEAD_OFFICE_ATM_NAME);
            repository.save(new Atm(Atm.HEAD_OFFICE_ATM_NAME, Atm.HEAD_OFFICE_ATM_LOCATION));
        }
    }
}
