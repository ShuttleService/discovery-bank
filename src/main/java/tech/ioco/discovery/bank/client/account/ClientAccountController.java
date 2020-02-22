package tech.ioco.discovery.bank.client.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tech.ioco.discovery.bank.Book;
import tech.ioco.discovery.bank.client.AuthenticatedClient;

@RestController
public class ClientAccountController {
    private static final Logger logger = LoggerFactory.getLogger(ClientAccountController.class);
    @Autowired
    private ClientAccountRepository repository;
    @Autowired
    private ClientAccountService service;

    @GetMapping("clientAccounts/{pageNumber}/{elementsPerPage}")
    public Page<ClientAccount> clientAccounts(@PathVariable("pageNumber") int pageNumber, @PathVariable("elementsPerPage") int elementsPerPage,
                                              @AuthenticationPrincipal AuthenticatedClient client) {
        logger.info("Finding client accounts from page {},{} elements per page for logged in client {}", pageNumber, elementsPerPage, client);
        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.by(Sort.Order.desc("displayBalance")));
        return repository.findByClient(client.getClient(), true, pageable);
    }

    @GetMapping("currencyClientAccounts/{pageNumber}/{itemsPerPage}")
    public Page<ClientAccount> currencyClientsAccounts(@PathVariable("pageNumber") int pageNumber, @PathVariable("itemsPerPage") int itemsPerPage, @AuthenticationPrincipal AuthenticatedClient client) {
        final Book book = new Book(pageNumber, itemsPerPage);
        logger.info("Finding currency accounts book {}, by {}", book, client);
        return service.currencyAccounts(client.getClient(), book);
    }
}
