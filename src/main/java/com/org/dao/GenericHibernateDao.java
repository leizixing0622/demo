package com.org.dao;

import com.org.entity.PageRequest;
import com.org.entity.PageResponse;
import com.org.entity.SortInfo;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public abstract class GenericHibernateDao<T extends Serializable, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK>{

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

    public T getById(PK id) {
        return getHibernateTemplate().get(entityClass, id);
    }

    public List<T> getAll() {
        return getHibernateTemplate().loadAll(entityClass);
    }

    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    public void save(T entity) {
        getHibernateTemplate().save(entity);
    }

    public void saveOrUpdate(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    public void deleteById(PK id) {
        this.delete(this.getById(id));
    }

    public int getTotalCount(Object o) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.entityClass);
        detachedCriteria.setProjection(Projections.rowCount());
        detachedCriteria.add(Example.create(o).ignoreCase());
        List<T> ts = getHibernateTemplate().findByCriteria(detachedCriteria);
        return Integer.parseInt(ts.get(0).toString());
    }

    public int getPageCount(int eachPageCount, Object o) {
        int pageCount;
        int actualEachPageCount;
        int totalCount = this.getTotalCount(o);
        actualEachPageCount = eachPageCount > totalCount ? totalCount : eachPageCount;
        if(totalCount > 0) {
            pageCount = (totalCount % actualEachPageCount == 0) ? (totalCount / actualEachPageCount) : (totalCount / actualEachPageCount) + 1;
        }else{
            pageCount = 0;
        }
        return pageCount;
    }

    public PageResponse<T> findByPageNumber(final PageRequest pageRequest, Object o) {
        List<T> ts = new ArrayList<T>();
        String sortFieldName = isEmpty(pageRequest.getSortField()) ? getDefaultSortFieldName() : pageRequest.getSortField();
        List<SortInfo> sortInfos = new ArrayList<SortInfo>();
        if(!isEmpty(sortFieldName)) {
            sortInfos = SortInfo.initSortFieldName(sortFieldName);
        }
        if(this.getTotalCount(o) > 0) {
            final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.entityClass);
            detachedCriteria.add(Example.create(o));
            for(SortInfo sortInfo : sortInfos) {
                if(sortInfo.getSortOrder().equals("desc")) {
                    detachedCriteria.addOrder(Order.desc(sortInfo.getSortFieldName()));
                }else{
                    detachedCriteria.addOrder(Order.asc(sortInfo.getSortFieldName()));
                }
            }
            ts = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
                public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
                    Criteria criteria = detachedCriteria.getExecutableCriteria(session);
                    criteria.setFirstResult(pageRequest.getStartIndex());
                    criteria.setMaxResults(pageRequest.getEachPageCount());
                    System.out.println(criteria.list().get(0).toString());
                    return criteria.list();
                }
            });
        }
        PageResponse pageResponse = new PageResponse<T>();
        pageResponse.setPageRequest(pageRequest);
        pageResponse.setTotalCount(this.getTotalCount(o));
        pageResponse.setResult(ts);
        return pageResponse;
    }
}
