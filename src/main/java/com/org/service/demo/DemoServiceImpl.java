package com.org.service.demo;

import com.org.dao.demo.DemoDaoImpl;
import com.org.entity.Websites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoDaoImpl demoDao;

    public Websites getWebsiteById(Long id) {
        return demoDao.getById(id);
    }


    public void saveWebsite(Websites websites){
        demoDao.save(websites);
    }
}
