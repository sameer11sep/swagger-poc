package com.egencia.sandbox.dao;

import com.egencia.sandbox.model.ResetPasswordToken;
import com.github.fakemongo.junit.FongoRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:application-context-test.xml")
public class ResetPasswordTokenDaoTest {

    @Rule
    public FongoRule fongoRule = new FongoRule();

    @Autowired
    AuthenticationTokenDao authenticationTokenDao;


    @Test
    public void shouldInsertAuthenticationTokenIntoDB() {
        addNewToken("abc",1234);
        assertNotNull(authenticationTokenDao.findById("abc"));
    }

    @Test
    public void shouldDeleteTokenByEmail(){
        addNewToken("abc",1234);
        authenticationTokenDao.removeTokensByTuId(1234);
        assertNull(authenticationTokenDao.findById("abc"));
    }

    private void addNewToken(String key,Integer tuId) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken(key);
        resetPasswordToken.setGpid(1);
        resetPasswordToken.setTuId(tuId);
        resetPasswordToken.setTpid(1);
        resetPasswordToken.setCreatedOn(new Date());
        authenticationTokenDao.save(resetPasswordToken);
    }




}