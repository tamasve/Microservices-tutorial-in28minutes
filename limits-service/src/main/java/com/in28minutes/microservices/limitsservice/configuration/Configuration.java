package com.in28minutes.microservices.limitsservice.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("limits-service")      // field values: from appl.prop, acc. to the variable names
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {

    private int minimum;
    private int maximum;
}
