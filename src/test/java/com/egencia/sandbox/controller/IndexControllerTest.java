package com.egencia.sandbox.controller;

import com.egencia.sandbox.dao.AuthenticationTokenDao;
import com.egencia.sandbox.model.ResetPasswordToken;
import com.github.fakemongo.junit.FongoRule;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:application-context-test.xml")
public class IndexControllerTest {

    @Rule
    public FongoRule fongoRule = new FongoRule();

    @Autowired
    IndexController indexController;

    @Autowired
    AuthenticationTokenDao dao;

    @Test
    public void shouldReturnApplicationVersionWhenPinged() {
        RestAssuredMockMvc.given().standaloneSetup(indexController).
                when()
                .get("/v1/ping")
                .then()
                .statusCode(200)
                .body("version", equalTo("1.0.0"))
                .body("name", equalTo("Egencia Sandbox"));
    }

    @Test
    @Ignore
    public void shouldReturnAValidLinkToResetPassword() {
        RestAssuredMockMvc.given().standaloneSetup(indexController)
                .contentType("application/json")
                .param("tuid",1234).param("tpid",1).param("gpid",1).param("expiryTimePeriod",24)
                .when()
                .patch("/v1/requestResetPassword")
                .then()
                .statusCode(200)
                .body("hyperLink", containsString("/v1/resetPassword/"));
    }

    @Test
    public void shouldInValidateUnknownTokenAndSendAppropriateResponse() {
        RestAssuredMockMvc.given().standaloneSetup(indexController).
                when()
                .get("/v1/resetPassword/abc")
                .then()
                .statusCode(200)
                .body("success", equalTo(false))
                .body("message", equalTo("Invalid Token, No Token could be found!!"));
    }


    @Test
    public void shouldValidateCorrectTokenAndSendAppropriateMessage() {
        addNewToken("abc", 1234, new Date(), 2);
        RestAssuredMockMvc.given().standaloneSetup(indexController).
                when()
                .get("/v1/resetPassword/abc")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("message", equalTo("Please reset your password"));
    }

    @Test
    public void shouldInValidateExpiredTokenAndSendAppropriateMessage() {
        addNewToken("xyz", 1234, new Date(), -1);
        RestAssuredMockMvc.given().standaloneSetup(indexController).
                when()
                .get("/v1/resetPassword/xyz")
                .then()
                .statusCode(200)
                .body("success", equalTo(false))
                .body("message", equalTo("Token has expired, please request another token to continue!!"));
    }


    private void addNewToken(String key, Integer tuId, Date createdOn, Integer expiresIn) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken(key);
        resetPasswordToken.setGpid(1);
        resetPasswordToken.setTuId(tuId);
        resetPasswordToken.setTpid(1);
        resetPasswordToken.setCreatedOn(createdOn);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, expiresIn);
        resetPasswordToken.setExpiresOn(calendar.getTime());
        dao.save(resetPasswordToken);
    }
}