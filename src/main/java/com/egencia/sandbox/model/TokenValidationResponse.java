package com.egencia.sandbox.model;

/**
 * Created by samarora on 9/18/14.
 */
public class TokenValidationResponse {

    private String message;

    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
