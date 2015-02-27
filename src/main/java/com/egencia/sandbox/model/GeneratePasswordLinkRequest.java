package com.egencia.sandbox.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class GeneratePasswordLinkRequest {

    @JsonProperty("tuid")
    private Integer userId;

    @JsonProperty("gpid")
    private Integer companyId;

    @JsonProperty("tpid")
    private Integer productId;

    @JsonProperty("expiryTimePeriod")
    private Integer expiration;

    public GeneratePasswordLinkRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getExpiration() {
        return expiration;
    }
}
