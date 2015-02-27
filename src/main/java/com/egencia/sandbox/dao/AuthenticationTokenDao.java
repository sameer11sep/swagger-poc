package com.egencia.sandbox.dao;

import com.egencia.sandbox.model.ResetPasswordToken;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Created by samarora on 9/17/14.
 */
@Component
public class AuthenticationTokenDao extends BaseDao<ResetPasswordToken> {

    public void removeTokensByTuId(Integer tuId){
        Query query=new BasicQuery("{\"tuId\":"+tuId+"}");
        mongoTemplate.remove(query, getEntityClass());
    }
    public ResetPasswordToken findObject(String token){
         Query query1= new Query();
        query1.addCriteria(Criteria.where("token").is(token));
        return mongoTemplate.findOne(query1,ResetPasswordToken.class);
    }

    @Override
    protected Class<ResetPasswordToken> getEntityClass() {
        return ResetPasswordToken.class;
    }
}
