package com.org.dao;

import com.org.entity.PageRequest;
import com.org.entity.PageResponse;
import com.org.entity.SearchInfo;
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

    public String getDefaultSearchFieldData() {
        return null;
    }

    public PageRequest initPageRequest(PageRequest pageRequest) {
        int totalCount = this.getTotalCount(pageRequest);
        if(totalCount == 0) {
            return null;
        }else{
            if(pageRequest.getEachPageCount() > totalCount) {
                pageRequest.setEachPageCount(totalCount);
            }
            int pageCount = (totalCount % pageRequest.getEachPageCount() == 0) ? (totalCount / pageRequest.getEachPageCount()) : (totalCount / pageRequest.getEachPageCount()) + 1;
            if(pageRequest.getPageNumber() > pageCount) {
                pageRequest.setPageNumber(pageCount);
            }
            return pageRequest;
        }
    }

    public int getTotalCount(PageRequest pageRequest) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.entityClass);
        detachedCriteria.setProjection(Projections.rowCount());
        String searchFieldData = isEmpty(pageRequest.getSearchContent()) ? getDefaultSearchFieldData() : pageRequest.getSearchContent();
        List<SearchInfo> searchInfos = SearchInfo.initSortFieldName(searchFieldData);
        for(SearchInfo searchInfo : searchInfos) {
            detachedCriteria.add(Restrictions.like(searchInfo.getSearchFieldName(), "%" + searchInfo.getSearchContent() + "%"));
        }
        List<T> ts = getHibernateTemplate().findByCriteria(detachedCriteria);
        int totalCount = Integer.parseInt(ts.get(0).toString());
        return totalCount;
    }

    public int getPageCount(PageRequest pageRequest) {
        int pageCount;
        int actualEachPageCount;
        int totalCount = this.getTotalCount(pageRequest);
        actualEachPageCount = pageRequest.getEachPageCount() > totalCount ? totalCount : pageRequest.getEachPageCount();
        if(totalCount > 0) {
            pageCount = (totalCount % actualEachPageCount == 0) ? (totalCount / actualEachPageCount) : (totalCount / actualEachPageCount) + 1;
        }else{
            pageCount = 0;
        }
        return pageCount;
    }

    public PageResponse<T> findByPageNumber(PageRequest pageRequest2, Object o) {
        final PageRequest pageRequest = this.initPageRequest(pageRequest2);
        if(pageRequest == null) {
            PageResponse pageResponse = new PageResponse<T>();
            pageResponse.setPageRequest(pageRequest);
            pageResponse.setTotalCount(0);
            pageResponse.setResult(new ArrayList<T>());
            return pageResponse;
        }else{
            String sortFieldData = isEmpty(pageRequest.getSortField()) ? getDefaultSortFieldData() : pageRequest.getSortField();
            List<SortInfo> sortInfos = SortInfo.initSortFieldName(sortFieldData);
            List<T> ts = new ArrayList<T>();
            if(this.getTotalCount(pageRequest) > 0) {
                final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.entityClass);
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
                        List<SearchInfo> searchInfos = SearchInfo.initSortFieldName(pageRequest.getSearchContent());
                        for(SearchInfo searchInfo : searchInfos) {
                            criteria.add(Restrictions.like(searchInfo.getSearchFieldName(), "%" + searchInfo.getSearchContent() + "%"));
                        }
                        criteria.setFirstResult(pageRequest.getStartIndex());
                        criteria.setMaxResults(pageRequest.getEachPageCount());
                        System.out.println(criteria.list().get(0).toString());
                        return criteria.list();
                    }
                });
            }
            PageResponse pageResponse = new PageResponse<T>();
            pageResponse.setPageRequest(pageRequest);
            pageResponse.setTotalCount(this.getTotalCount(pageRequest));
            pageResponse.setResult(ts);
            return pageResponse;
        }
    }
}
