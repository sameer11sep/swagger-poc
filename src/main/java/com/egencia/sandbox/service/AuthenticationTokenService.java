package com.egencia.sandbox.service;

import com.egencia.sandbox.dao.AuthenticationTokenDao;
import com.egencia.sandbox.model.ResetPasswordToken;
import com.egencia.sandbox.model.TokenValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by samarora on 9/17/14.
 */
@Component
public class AuthenticationTokenService {

    @Autowired
    private AuthenticationTokenDao dao;

    @Value("${password.reset.endpoint}")
    private String resetPasswordUrl;


    public String generateToken(int tuId, int gpId, int tpid,int tokenExpirationPeriod) {
        String token = generateAndSaveToken(tuId, gpId, tpid,tokenExpirationPeriod);
        return resetPasswordUrl +"/" + token;
    }


    private String generateAndSaveToken(Integer tuId, int gpId, int tpid,int tokenExpirationPeriod) {
        UUID uuid = UUID.randomUUID();
        ResetPasswordToken token = new ResetPasswordToken();
        String uid = uuid.toString();
        token.setToken(uid);
        token.setGpid(gpId);
        token.setTuId(tuId);
        token.setTpid(tpid);
        Date currentTime = now();
        token.setCreatedOn(currentTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.HOUR,tokenExpirationPeriod);
        token.setExpiresOn(calendar.getTime());
        dao.save(token);
        return uid;
    }

    private Date now() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
        calendar.setTime(new Date());
        return calendar.getTime();
    }

    public TokenValidationResponse validateToken(String token) {
        ResetPasswordToken resetPasswordToken = dao.findObject(token);
        if (resetPasswordToken != null) {
            if (hasTokenExpired(resetPasswordToken)) {

                return buildResponse("Token has expired, please request another token to continue!!", false);
            }
          //  dao.remove(resetPasswordToken);
            return buildResponse("Please reset your password", true);
        }
        System.out.println("Invalid Token,"+token+" No Token could be found!!");
        return buildResponse("Invalid Token, No Token could be found!!", false);
    }

    private boolean hasTokenExpired(ResetPasswordToken resetPasswordToken) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(resetPasswordToken.getExpiresOn());
        Date now = now();
        return (calendar.getTime().before(now) || resetPasswordToken.getCreatedOn().after(now));
    }

    private TokenValidationResponse buildResponse(String message, boolean success) {
        TokenValidationResponse response = new TokenValidationResponse();
        response.setSuccess(success);
        response.setMessage(message);
        return response;
    }

    public void setResetPasswordUrl(String resetPasswordUrl) {
        this.resetPasswordUrl = resetPasswordUrl;
    }


}
