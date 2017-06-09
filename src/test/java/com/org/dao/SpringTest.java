package com.org.dao;

import com.org.dao.demo.DemoDaoImpl;
import com.org.entity.PageRequest;
import com.org.entity.Websites;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/applicationContext.xml"})
public class SpringTest {

    @Autowired
    private DemoDaoImpl demoDao;

    @Test
    public void testSave(){
        PageRequest pageRequest = new PageRequest(2, 3);
        pageRequest.setSearchContent("name 淘");
        List<Websites> websitesList = demoDao.findByPageNumber(pageRequest, new Websites()).getResult();
        for(int i = 0; i < websitesList.size(); i ++) {
            System.out.println(websitesList.get(i));
        }
        System.out.println(demoDao.getTotalCount(pageRequest));
    }
}
