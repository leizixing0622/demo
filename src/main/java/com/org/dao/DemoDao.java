package com.org.dao;

import com.org.entity.Websites;
import org.springframework.stereotype.Repository;

@Repository
public class DemoDao extends BaseHibernateDao<Websites, String>{

    @Override
    public Class<Websites> getEntityClass() {
        return Websites.class;
    }
}
