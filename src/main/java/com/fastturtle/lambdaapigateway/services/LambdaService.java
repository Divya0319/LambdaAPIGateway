package com.fastturtle.lambdaapigateway.services;

import com.fastturtle.lambdaapigateway.models.Request;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

public class LambdaService {

    private LambdaClient lambdaClient;

    public LambdaService(LambdaClient lambdaClient) {
        this.lambdaClient = lambdaClient;
    }

    public String invokeLambdaForTTSUsingPolly(Request request) {
        String payload;

        if(request.getVoiceId() != null) {
            payload = "{"
                    + "\"text\": " + request.getText() + ", "
                    + "\"voiceId\": " + request.getVoiceId()
                    +"}";
        } else {
            payload = "{"
                    + "\"text\": " + request.getText()
                    +"}";
        }

        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName("LambdaForTTSUsingPolly")
                .payload(SdkBytes.fromUtf8String(payload))
                .build();

        InvokeResponse response = lambdaClient.invoke(invokeRequest);

        return response.payload().asUtf8String();
    }
}
