package com.in28minutes.microservices.limitsservice.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *  This class sets the values to the props from the app-prop file
 */
@Component
@ConfigurationProperties("limits-service")      // field values: from appl.prop with the "limits-service" prefix, acc. to the variable names:  "limits-service.<varname>"
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {

    private int minimum;
    private int maximum;
}
