package com.egencia.sandbox.service;

import com.egencia.sandbox.dao.AuthenticationTokenDao;
import com.egencia.sandbox.model.ResetPasswordToken;
import com.egencia.sandbox.model.TokenValidationResponse;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ResetPasswordTokenServiceTest {

    public static final Integer TUID = 1234;

    @InjectMocks
    AuthenticationTokenService service = new AuthenticationTokenService();

    @Mock
    AuthenticationTokenDao dao;

    @Test
    public void shouldGenerateTokenAndSendEmail() {
        service.setResetPasswordUrl("http://localhost:8080/swagger-poc/v1/resetPassword");
        String url = service.generateToken(TUID, 1, 1, 1);
        assertTrue(url.contains("resetPassword"));
        Mockito.verify(dao, Mockito.times(1)).save(Mockito.any(ResetPasswordToken.class));
        Mockito.verifyNoMoreInteractions(dao);
    }

    @Test
    @Ignore
    public void shouldValidateTokenSuccessfully() {
        String key = "abc";
        ResetPasswordToken token = buildAuthenticationToken(key, TUID, -1, 2);
        Mockito.when(dao.findById(key)).thenReturn(token);
        TokenValidationResponse tokenValidationResponse = service.validateToken(key);
        assertTrue(tokenValidationResponse.isSuccess());
    }

    @Test
    public void shouldRejectExpiredToken() {
        String key = "abc";
        ResetPasswordToken token = buildAuthenticationToken(key, TUID, -1, -2);
        Mockito.when(dao.findById(key)).thenReturn(token);
        TokenValidationResponse tokenValidationResponse = service.validateToken(key);
        assertTrue("Assertion Failed,Token is still valid", !tokenValidationResponse.isSuccess());
    }


    @Test
    public void shouldRejectUnknownToken() {
        String key = "abc";
        ResetPasswordToken token = buildAuthenticationToken(key, TUID, -1, 2);
        Mockito.when(dao.findById(key)).thenReturn(token);
        TokenValidationResponse tokenValidationResponse = service.validateToken("xyz");
        assertTrue("Assertion Failed,Token was found in the database", !tokenValidationResponse.isSuccess());
    }

    private ResetPasswordToken buildAuthenticationToken(String token, Integer tuId, Integer createdOn, Integer expirationTimeInHours) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken(token);
        resetPasswordToken.setGpid(1);
        resetPasswordToken.setTuId(tuId);
        resetPasswordToken.setTpid(1);
        resetPasswordToken.setCreatedOn(getDate(createdOn));
        resetPasswordToken.setExpiresOn(getDate(expirationTimeInHours));
        return resetPasswordToken;
    }

    private Date getDate(Integer createdOn) {
        Calendar date = Calendar.getInstance();
        date.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
        date.setTime(new Date());
        date.add(Calendar.HOUR, createdOn);
        return date.getTime();
    }


}