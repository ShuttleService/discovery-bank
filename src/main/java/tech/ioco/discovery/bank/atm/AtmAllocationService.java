package tech.ioco.discovery.bank.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.ioco.discovery.bank.client.Client;
import tech.ioco.discovery.bank.client.ClientRepository;
import tech.ioco.discovery.bank.client.account.ClientAccount;
import tech.ioco.discovery.bank.client.account.ClientAccountRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AtmAllocationService {
    private static final Logger logger = LoggerFactory.getLogger(AtmAllocationService.class);
    @Autowired
    private AtmAllocationRepository repository;
    @Autowired
    private AtmRepository atmRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientAccountRepository clientAccountRepository;

    public List<WithdrawalAllocation> withdraw(String atmName, Client inputClient, String clientAccountNumber, BigDecimal inputAmount) {
        logger.info("Withdrawing {} from {} by {}-{}", inputAmount, atmName, inputClient, clientAccountNumber);
        Optional<Client> client = clientRepository.findById(inputClient.getClientId());
        if (client.isEmpty()) {
            logger.error("The logged in client {} was not found. Aborting", inputClient);
            return Collections.emptyList();
        }

        Optional<ClientAccount> clientAccount = clientAccountRepository.findById(clientAccountNumber);

        if (clientAccount.isEmpty()) {
            logger.error("Attempting to withdraw from an non existent client account. Aborting");
            return Collections.emptyList();
        }
        if (!clientAccount.get().getAccountType().isTransactional()) {
            logger.error("Withdrawal from non transactional account {}. Aborting ", clientAccount);
            return Collections.emptyList();
        }
        if (clientAccount.get().getClient().getClientId() != inputClient.getClientId()) {
            logger.error("The account number {} does not belong to this client {} attempting a withdrawal ", clientAccountNumber, inputClient);
            return Collections.emptyList();
        }
        if (clientAccount.get().getDisplayBalance().compareTo(inputAmount) < 0) {
            logger.warn("Client {} is attempting to withdraw an amount {} greater than their balance of {}. Aborting ", client, inputAmount,
                    clientAccount.get().getDisplayBalance());
            throw new RuntimeException("Insufficient funds");
        }
        Optional<Atm> atm = atmRepository.findByName(atmName);
        if (atm.isEmpty()) {
            logger.error("Failed to find the ATM {}. Aborting", atmName);
            throw new RuntimeException("ATM not registered or unfunded");
        }
        Page<AtmAllocation> atmAllocations = repository.findByAtm(atm.get(), PageRequest.of(0, 50, Sort.by(Sort.Order.desc("denomination.value"))));
        if (atmAllocations.isEmpty()) {
            logger.warn("ATM {} has run of money. Please reload", atm);
            throw new RuntimeException("ATM not registered or unfunded");
        }

        double atmTotalAllocation = atmAllocations.getContent().stream().map(atmAllocation ->
                atmAllocation.getDenomination().getValue().doubleValue() * atmAllocation.getCount()).mapToDouble(amount -> amount).sum();
        if (atmTotalAllocation < inputAmount.doubleValue()) {
            logger.warn("Client withdrawal amount {} is more than the total allocation, suggesting a different amount of {}", inputAmount, atmTotalAllocation);
            throw new RuntimeException(String.format("Amount not available, would you like to draw R%.2f", atmTotalAllocation));
        }
        List<WithdrawalAllocation> withdrawalAllocations = new ArrayList<>();
        long amount = inputAmount.longValue();
        logger.debug("The withdrawal amount {} as long {}", inputAmount, amount);

        for (AtmAllocation atmAllocation : atmAllocations.getContent()) {
            long numberOfNotes = amount / atmAllocation.getDenomination().getValue().longValue();
            if (numberOfNotes > 0) {
                amount -= numberOfNotes * atmAllocation.getDenomination().getValue().longValue();
                withdrawalAllocations.add(new WithdrawalAllocation(atmAllocation.getDenomination(), numberOfNotes));
                logger.info("Adding {} notes for denomination {} ", numberOfNotes, atmAllocation.getDenomination());
            }
        }
        clientAccountRepository.withdraw(clientAccount.get(), inputAmount);
        return withdrawalAllocations;
    }
}
