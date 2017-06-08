package com.org.dao.demo;

import com.org.dao.GenericHibernateDao;
import com.org.entity.Websites;
import org.springframework.stereotype.Repository;

@Repository
public class DemoDaoImpl extends GenericHibernateDao<Websites, Long> implements DemoDao{


}
