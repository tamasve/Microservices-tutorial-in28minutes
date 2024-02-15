package com.in28minutes.microservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.NoSuchElementException;

@RestController
public class CurrencyConversionController {

    // for the Feign version...
    private CurrencyExchangeProxy currencyExchangeProxy;
    @Autowired
    public void setCurrencyExchangeProxy(CurrencyExchangeProxy currencyExchangeProxy) {
        this.currencyExchangeProxy = currencyExchangeProxy;
    }

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        // this performs an HTTP request to another microservice
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class,
                uriVariables);

        CurrencyConversion currencyConversion = responseEntity.getBody();

        if (currencyConversion == null) throw new NoSuchElementException("Requested currency exchange does not exist.");

        return new CurrencyConversion(
                currencyConversion.getId(),
                from, to, quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply( currencyConversion.getConversionMultiple() ),
                currencyConversion.getEnvironment() + " << rest template");
    }


    // the Feign version - using a proxy instead of a special method call with parameters and data check:

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity) {

        CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);

        // 1 code line instead of all these previous:

//        HashMap<String, String> uriVariables = new HashMap<>();
//        uriVariables.put("from", from);
//        uriVariables.put("to", to);
//
//        // this performs an HTTP request to another microservice
//        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
//                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
//                CurrencyConversion.class,
//                uriVariables);
//
//        CurrencyConversion currencyConversion = responseEntity.getBody();
//
//        if (currencyConversion == null) throw new NoSuchElementException("Requested currency exchange does not exist.");

        return new CurrencyConversion(
                currencyConversion.getId(),
                from, to, quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply( currencyConversion.getConversionMultiple() ),
                currencyConversion.getEnvironment() + " << feign proxy" );
    }
}
