package com.org.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T extends Serializable, PK extends Serializable> {

    //根据主键获取实体
    public T getById(PK id);
    //获取全部实体
    public List<T> getAll();
    //更新实体
    public void update(T entity);
    //新增实体
    public void save(T entity);
    //增加或者更新实体
    public void saveOrUpdate(T entity);
    //删除实体
    public void delete(T entity);
    //根据主键删除实体
    public void deleteById(PK id);
}
