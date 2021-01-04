package com.lylbp;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.activiti.spring.boot.SecurityAutoConfiguration.class,
        SecurityAutoConfiguration.class})
public class JavaModelApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaModelApplication.class, args);
    }
}
