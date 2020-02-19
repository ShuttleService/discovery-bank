package tech.ioco.discovery.bank.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientTypeService {
    private final Logger logger = LoggerFactory.getLogger(ClientTypeService.class);
    @Autowired
    private ClientTypeRepository repository;

    public void addDefaults() {
        logger.info("Adding default client types.");
        Optional<ClientType> client = repository.findById("IC");
        if (client.isEmpty()) {
            logger.info("Investment Client is not in the repository. Adding it.");
            repository.save(new ClientType("IC", "This is an investment client"));
        }
        client = repository.findById("TC");
        if (client.isEmpty()) {
            logger.info("Transactions Client is not in the repository. Adding it.");
            repository.save(new ClientType("TC", "This is a transactional client"));
        }
    }
}
