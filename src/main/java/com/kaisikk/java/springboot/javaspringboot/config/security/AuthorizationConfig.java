package com.kaisikk.java.springboot.javaspringboot.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthorizationConfig implements WebMvcConfigurer {


    public void addViewController(ViewControllerRegistry registry){

        registry.addViewController("/authorization").setViewName("authorization");
        registry.addViewController("/").setViewName("authorization");
        registry.addViewController("/hellopage").setViewName("hellopage");
        registry.addViewController("/login").setViewName("login");

    }

}



