package com.egencia.sandbox.model;

/**
 * Created by samarora on 9/18/14.
 */
public class ResetPasswordForm {

    public ResetPasswordForm() {
    }

    public ResetPasswordForm(String email) {
        this.email = email;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
