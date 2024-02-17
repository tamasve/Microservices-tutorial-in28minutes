package com.in28minutes.microservices.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")       // name = "default" >> 3, I can give any other value in app.props: resilience4j.retry.instances.sample-api.maxAttempts=5   using this name here...
    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")      // after some retries it does not call the method, instead directly return the hardcoded response back - 3 states: https://resilience4j.readme.io/docs/circuitbreaker
// -- to test it: send 10 api calls from ubuntu:  watch -n 0.1 curl http://192.168.0.23:8000/sample-api  (ip address from ipconfig, localhost does not work)
//    @RateLimiter(name = "default")      // max = 10000 calls during 10s + settings in app.props
//    @Bulkhead(name = "default")         // how many concurrent calls?
    public String sampleApi() {
        logger.info("Sample API call received");
        ResponseEntity<String> entity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return entity.getBody();
//        return "sample-api";
    }

    // the fallback method of the previous method - after x retries this will be invoked (Throwable parameter is necessary!)
    public String hardcodedResponse(Exception ex) {
        return "Fallback: the API does not work due to " + ex.getMessage();
    }
}
