package com.ridetour.backend.response;

/**
 * Created by eyal on 5/28/2016.
 */
public class MessageResponse {
    private final String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
