package com.org.service.demo;

import com.org.entity.Websites;

public interface DemoService {

   Websites getWebsiteById(Long id);
   void saveWebsite(Websites websites);
}
