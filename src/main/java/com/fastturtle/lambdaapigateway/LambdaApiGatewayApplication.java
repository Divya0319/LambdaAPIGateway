package com.fastturtle.lambdaapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class LambdaApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LambdaApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("lambda_tts_route", r -> r
                        .path("/callTextToAudioLambda")
                        .filters(f -> f
                                .modifyRequestBody(String.class, String.class, (exchange, body) -> {
                                return Mono.just(body);
                                })
                        ).uri("http://localhost:8080"))
                        .build();
    }

}
