package com.spring.boot.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.config.ConfigBean;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HelloController {
	
	@Autowired
	ConfigBean mConfigBean;
	
//	@Autowired
//	MultipleProperities mDevconfigBean;

    @RequestMapping("/hello")
    public String index() {
    		log.info("testing purpose: {}", "info level");
    		log.debug("testing purpose: {}", "debug level");
        return "Hello,World!";
    }

}