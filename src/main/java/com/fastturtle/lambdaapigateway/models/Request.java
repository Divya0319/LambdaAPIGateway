package com.fastturtle.lambdaapigateway.models;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Request {

    private String text;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String voiceId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVoiceId() {
        return voiceId;
    }

    public void setVoiceId(String voiceId) {
        this.voiceId = voiceId;
    }
}
