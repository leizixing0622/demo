package com.org.service;

import com.org.dao.DemoDao;
import com.org.entity.Websites;
import org.springframework.beans.factory.annotation.Autowired;

public interface DemoService {

   public Websites getWebsiteById(String id);
}
