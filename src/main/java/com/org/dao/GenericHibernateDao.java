package com.org.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericHibernateDao<T extends Serializable, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, Pk>{

    //实体类类型由构造方法直接赋值
    private Class<T> entityClass;

    //构造方法如下
    public GenericHibernateDao(){
        this.entityClass = null;
        Class c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
    }

    public T getById(Pk id) {
        return (T)getHibernateTemplate().get(entityClass, id);
    }

    public List<T> getAll() {
        return null;
    }

    public void update(T entity) {

    }

    public void save(T entity) {

    }

    public void saveOrUpdate(T entity) {

    }

    public void delete(T entity) {

    }

    public void deleteById(Pk id) {

    }
}
