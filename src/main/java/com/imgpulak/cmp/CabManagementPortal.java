package com.imgpulak.cmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class CabManagementPortal {

  private final static Logger LOG = LoggerFactory.getLogger(CabManagementPortal.class);

  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(CabManagementPortal.class, args);

    LOG.debug("Let's inspect the beans provided by Spring Boot:");

    String[] beanNames = ctx.getBeanDefinitionNames();
    Arrays.sort(beanNames);
    for (String beanName : beanNames) {
      LOG.debug(beanName);
    }
  }
}
