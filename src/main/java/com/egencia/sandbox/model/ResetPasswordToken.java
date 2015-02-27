package com.egencia.sandbox.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by samarora on 9/17/14.
 */
@Document(collection = "auth_tokens_test")

public class ResetPasswordToken {

    @Id
    private String token;

    private Integer tuId;

    private Integer tpid;

    private Integer gpid;

    private Date createdOn;

    @Indexed(expireAfterSeconds = 240)
    private Date expiresOn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTuId() {
        return tuId;
    }

    public void setTuId(Integer tuId) {
        this.tuId = tuId;
    }

    public int getTpid() {
        return tpid;
    }

    public void setTpid(int tpid) {
        this.tpid = tpid;
    }

    public int getGpid() {
        return gpid;
    }

    public void setGpid(int gpid) {
        this.gpid = gpid;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }
}
