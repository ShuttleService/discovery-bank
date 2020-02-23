package tech.ioco.discovery.bank.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    private ClientRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientSubTypeRepository clientSubTypeRepository;

    public void addDefaults() {
        logger.info("Adding test client and admin client please wait...");
        Optional<ClientSubType> clientSubType = clientSubTypeRepository.findById("STII");
        Optional<Client> client = repository.findByUsername("admin");
        if (client.isEmpty()) {
            logger.info("Adding an admin to the repository");
            repository.save(new Client("admin", passwordEncoder.encode("adminPassword"), Title.DR, "Admin", "Discovery", new Date(),
                    clientSubType.get(), Role.ADMIN));
        }
        client = repository.findByUsername("test");
        if (client.isEmpty()) {
            logger.info("Adding a test user to the repository");
            repository.save(new Client("test", passwordEncoder.encode("testPassword"), Title.DR, "Test", "Discovery", new Date(),
                    clientSubType.get(), Role.CLIENT));
        }
    }
}
