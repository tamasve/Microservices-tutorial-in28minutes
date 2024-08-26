package com.in28minutes.microservices.apigateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

// To configure the paths of the common API

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
//        Function<PredicateSpec, Buildable<Route>> routeFunction
//                = p -> p.path("/get")
//                                .filters( f -> f.addRequestHeader("MyHeader", "MyURI")      // my parameter into the "headers"
//                                                            .addRequestParameter("MyParam", "MyValue"))         // my parameter into the "args"
//                                .uri("http://httpbin.org:80");     // redirect to this URI if the request is /get
        return builder.routes()
//                .route(routeFunction)         // this would be if we used the above Function definition, but instead we use directly lambdas
                .route(
                        p -> p.path("/get")     // the route to redirect - "match" = "predicate"
                                .filters( f -> f.addRequestHeader("CustomHeaderParameter", "SpecURI")      // my parameter into the "headers" - filter = what needs to be done once a request is matched
                                        .addRequestParameter("Parameter", "ParValue"))         // my parameter into the "args" data part
                                .uri("http://httpbin.org:80")     // redirect to this URI if the request is "/get"
                )
                .route(
                        p -> p.path("/currency-exchange/**")
                                        .uri("lb://currency-exchange")      // lb: load balancer, this invokes the url from the Eureka Server = no duplication (app name + url) + the port always is the API gateway's port: 8765
                )                                               // http://localhost:8765/currency-exchange/from/USD/to/INR
                .route(
                        p -> p.path("/currency-conversion/**")
                                .uri("lb://currency-conversion")      // lb: load balancer
                )
                .route(
                        p -> p.path("/currency-conversion-feign/**")
                                .uri("lb://currency-conversion")      // lb: load balancer
                )
                .route(
                        p -> p.path("/currency-conversion-new/**")      // to lead ... -new url onto ... -feign
                                .filters( f -> f.rewritePath(
                                        "/currency-conversion-new/(?<segment>.*)",
                                        "/currency-conversion-feign/${segment}") )
                                .uri("lb://currency-conversion")      // lb: load balancer
                )
                .build();
    }

}
