package tech.ioco.discovery.bank.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientSubTypeService {
    private final static Logger logger = LoggerFactory.getLogger(ClientSubTypeService.class);

    @Autowired
    private ClientSubTypeRepository repository;
    @Autowired
    private ClientTypeRepository clientTypeRepository;

    public void addDefaults() {
        logger.info("Adding default client sub types.");
        Optional<ClientType> investment = clientTypeRepository.findById("IC");
        Optional<ClientType> transactional = clientTypeRepository.findById("TC");
        Optional<ClientSubType> clientSubType = repository.findById("STII");
        if (clientSubType.isEmpty()) {
            logger.info("Adding individual investment client sub type to the repository");
            repository.save(new ClientSubType("STII", investment.get(), "This is an individual investment client sub type"));
        }
        clientSubType = repository.findById("STIT");
        if (clientSubType.isEmpty()) {
            logger.info("Adding Individual transactional sub type to the repository");
            repository.save(new ClientSubType("STIT", transactional.get(), "This is an individual transactional client sub type"));
        }
        clientSubType = repository.findById("STCI");
        if (clientSubType.isEmpty()) {
            logger.info("Adding Company investment sub type to the repository");
            repository.save(new ClientSubType("STCI", investment.get(), "This is a company investment client sub type"));
        }
        clientSubType = repository.findById("STCT");
        if (clientSubType.isEmpty()) {
            logger.info("Adding Company Transactional sub type to the repository");
            repository.save(new ClientSubType("STCT", transactional.get(), "This is a company transactional client sub type"));
        }
    }

}
