package com.in28minutes.microservices.limitsservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  This class uses the prop values from Configuration
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Limits {
    private int minimum;
    private int maximum;
}
