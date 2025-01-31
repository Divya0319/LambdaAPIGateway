package com.fastturtle.lambdaapigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {


    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/callTextToAudioLambda")
                        .filters(f -> f.stripPrefix(0)
                                .preserveHostHeader())
//                        .filters(f -> f.modifyRequestBody(String.class, String.class,
//                                (exchange, requestBody) -> Mono.just(requestBody)))
                        .uri("http://localhost:8081"))
                .build();
    }
}
