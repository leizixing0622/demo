package com.org.dao;

import com.org.service.DemoServiceImpl;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class SpringTest {

    @Autowired
    private DemoServiceImpl demoService;
    @Autowired
    private DemoDao demoDao;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testSave(){

        System.out.println(demoService.getWebsiteById("1"));
    }
}
