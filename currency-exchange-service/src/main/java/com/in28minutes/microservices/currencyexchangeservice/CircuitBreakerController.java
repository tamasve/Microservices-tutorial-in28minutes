package com.in28minutes.microservices.currencyexchangeservice;

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
    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")       // name = "default" >> 3, I can give any other value in app.props: resilience4j.retry.instances.sample-api.maxAttempts=5   using this name here...
    public String sampleApi() {
        logger.info("Sample API call received");
        ResponseEntity<String> entity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return entity.getBody();
    }

    // the fallback method of the previous method - after x retries this will be invoked (Throwable parameter is necessary!)
    public String hardcodedResponse(Exception ex) {
        return "Fallback: the API does not work due to " + ex.getMessage();
    }
}
