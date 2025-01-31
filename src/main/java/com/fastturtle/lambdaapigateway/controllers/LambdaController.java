package com.fastturtle.lambdaapigateway.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastturtle.lambdaapigateway.models.Request;
import com.fastturtle.lambdaapigateway.models.Response;
import com.fastturtle.lambdaapigateway.services.LambdaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LambdaController {

    private final LambdaService lambdaService;

    public LambdaController(LambdaService lambdaService) {
        this.lambdaService = lambdaService;
    }

    @PostMapping("/callTextToAudioLambda")
    public Response printTTSUsingPollyLambdaResponse(@RequestBody Request request) {
        String lambdaResponse = lambdaService.invokeLambdaForTTSUsingPolly(request);

        if(lambdaResponse.startsWith("\"") && lambdaResponse.endsWith("\"")) {
            lambdaResponse = lambdaResponse.substring(1, lambdaResponse.length() - 1);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            return objectMapper.readValue(lambdaResponse, Response.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
