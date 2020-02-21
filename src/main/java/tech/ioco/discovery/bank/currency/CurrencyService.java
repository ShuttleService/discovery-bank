package tech.ioco.discovery.bank.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyService {
    private final static Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    @Autowired
    private CurrencyRepository repository;

    public void addDefaults() {
        logger.info("Adding default currencies to the repository");
        Optional<Currency> currency = repository.findById("USD");
        if (currency.isEmpty()) {
            logger.info("Adding USD");
            repository.save(new Currency("USD", "United States Dollar", 2));
        }
        currency = repository.findById("ZAR");
        if (currency.isEmpty()) {
            logger.info("Adding ZAR ");
            repository.save(new Currency("ZAR", "South African Rand", 2));
        }
        currency = repository.findById("EUR");
        if (currency.isEmpty()) {
            logger.info("Adding Euro");
            repository.save(new Currency("EUR", "Euro", 2));
        }
    }
}
