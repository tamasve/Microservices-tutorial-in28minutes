package com.in28minutes.microservices.currencyconversionservice;

// this should be an interface! otherwise it won't work

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//@FeignClient(name="currency-exchange", url="localhost:8000")          // = spring.application.name in app.props of the service that we want to invoke using this class
@FeignClient(name="currency-exchange")          // this way the Load Balancer (with Feign) is balancing among the services
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")       // this method signature+mapping is from currency-exchange-service's controllers (return type changed...)
    public CurrencyConversion retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to);
}
