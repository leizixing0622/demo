package com.org.dao;

import com.org.entity.PageRequest;
import com.org.entity.PageResponse;

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
    //查询总条数
    public int getTotalCount(Object o);
    //根据每页条数，得到页数
    public int getPageCount(int eachPageCount, Object o);
    //根据页码进行查找
    public PageResponse<T> findByPageNumber(PageRequest pageRequest, Object o);
    //得到默认的排序列
    public String getDefaultSortFieldName();
}
