package com.egencia.sandbox.model;

/**
 * Created by samarora on 9/17/14.
 */
public class ResetPasswordResponse {

    private String message;

    private String hyperLink;

    public ResetPasswordResponse(String s, String url) {
        this.message=s;
        this.hyperLink=url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHyperLink() {
        return hyperLink;
    }

    public void setHyperLink(String hyperLink) {
        this.hyperLink = hyperLink;
    }
}
