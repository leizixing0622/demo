package com.org.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;

public abstract class BaseHibernateDao<E, PK extends Serializable> extends HibernateDaoSupport {

    public abstract Class<E> getEntityClass();

    public E getById(PK id){
        return getHibernateTemplate().get(getEntityClass(), id);
    }
}
