package tech.ioco.discovery.bank.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientUserService implements UserDetailsService {

    @Autowired
    private ClientRepository repository;
    private final Logger logger = LoggerFactory.getLogger(ClientUserService.class);

    @Override
    public AuthenticatedClient loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user {} by username", username);
        Optional<Client> optionalUser = repository.findByUsername(username);
        logger.info("Found optional  {} ", optionalUser);
        AuthenticatedClient authenticatedUser;
        if (optionalUser.isPresent()) {
            authenticatedUser = new AuthenticatedClient(optionalUser.get());
        } else {
            throw new UsernameNotFoundException("Could not find user with username " + username);
        }
        logger.info("Found and returning user {}", authenticatedUser);

        return authenticatedUser;
    }
}
