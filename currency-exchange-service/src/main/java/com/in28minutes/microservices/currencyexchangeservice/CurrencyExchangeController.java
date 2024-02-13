package com.in28minutes.microservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;        // to obtain environment information aut.ly

    private CurrencyExchangeRepository currencyExchangeRepository;
    @Autowired
    public void setCurrencyExchangeRepository(CurrencyExchangeRepository currencyExchangeRepository) {
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to) {

        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
        if (currencyExchange == null)  throw new NoSuchElementException("Unable to find data");

        currencyExchange.setEnvironment( environment.getProperty("local.server.port") );
        return currencyExchange;
    }
}
