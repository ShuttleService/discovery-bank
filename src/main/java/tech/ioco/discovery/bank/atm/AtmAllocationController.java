package tech.ioco.discovery.bank.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ioco.discovery.bank.client.AuthenticatedClient;

import java.util.List;

@RestController
public class AtmAllocationController {
    private Logger logger = LoggerFactory.getLogger(AtmAllocationController.class);
    @Autowired
    private AtmAllocationService service;

    @PutMapping("withdraw")
    public List<WithdrawalAllocation> withdraw(@RequestBody WithdrawalRequest withdrawalRequest, @AuthenticationPrincipal AuthenticatedClient client) {
        logger.info("Withdrawing with request {} by {}", null, client);
        return service.withdraw(withdrawalRequest.getAtmId(), withdrawalRequest.getClient(), withdrawalRequest.getClientAccountNumber(), withdrawalRequest.getAmount());
    }

}
