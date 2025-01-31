package com.fastturtle.lambdaapigateway.services;

import com.fastturtle.lambdaapigateway.models.Request;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

@Service
public class LambdaService {

    private final LambdaClient lambdaClient;

    public LambdaService() {
        this.lambdaClient = LambdaClient.builder().build();
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
