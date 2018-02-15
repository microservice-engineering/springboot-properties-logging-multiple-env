package com.spring.boot.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

//配置文件注入
@Configuration
@ConfigurationProperties(prefix="com.vgc")
@PropertySource("classpath:test.properties")
@Data
public class ConfigBean {
	
	private String type;
	private String provider;
}
