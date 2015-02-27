package com.egencia.sandbox.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by samarora on 9/17/14.
 */
public abstract class BaseDao<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    public void save(T entity){
        mongoTemplate.save(entity);
    }

    public void remove(T entity){
        mongoTemplate.remove(entity);
    }

    public void remove(Query query){
        mongoTemplate.remove(query,getEntityClass());
    }

    public T findById(Object id){
       return mongoTemplate.findById(id,getEntityClass());
    }

    protected abstract Class<T> getEntityClass();

}
