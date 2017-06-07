package com.org.service;

import com.org.dao.DemoDao;
import com.org.entity.Websites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoDao demoDao;

    public Websites getWebsiteById(String id) {
        return demoDao.getById("1");
    }
}
