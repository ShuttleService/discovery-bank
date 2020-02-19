package tech.ioco.discovery.bank.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @GetMapping
    public String ping() {
        return "pong";
    }
}
