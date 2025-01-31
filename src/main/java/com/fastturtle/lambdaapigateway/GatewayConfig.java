package com.fastturtle.lambdaapigateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastturtle.lambdaapigateway.models.Request;
import com.fastturtle.lambdaapigateway.services.LambdaService;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {


    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder, LambdaService lambdaService, ObjectMapper objectMapper) {
        return builder.routes()
                .route("lambda_tts_route", r -> r
                        .path("/callTextToAudioLambda")
                        .filters(f -> f.addResponseHeader("Content-Type", "application/json")
                                .modifyRequestBody(String.class, String.class,
                                        (exchange, requestBodyStr) -> {
                                            try {
                                                Request request = objectMapper.readValue(requestBodyStr, Request.class);
                                                String lambdaResponse = lambdaService.invokeLambdaForTTSUsingPolly(request);

                                                System.out.println("Lambda Response in Gateway: " + lambdaResponse);

                                                // Return the Lambda response (which is a String)
                                                return Mono.just(lambdaResponse);
                                            } catch (JsonProcessingException e) {
                                                return Mono.error(new RuntimeException("Error parsing request JSON", e));
                                            }
                                        })
                                .modifyResponseBody(String.class, String.class,
                                        (exchange, responseBody) -> {
                                            System.out.println("Modifying Response in Gateway: " + responseBody);
                                            return Mono.just(responseBody);
                                        }))

                        .uri("forward:/dummy"))
                .build();
    }
}
