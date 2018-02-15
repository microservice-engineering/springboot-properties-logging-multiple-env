package com.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.spring.boot.config.ConfigBean;
import com.spring.boot.config.MultipleProperities;

@SpringBootApplication
public class Chapter1Application {

	public static void main(String[] args) {
	    SpringApplication.run(Chapter1Application.class, args);
	}

}
