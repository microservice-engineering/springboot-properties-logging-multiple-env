package com.spring.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="com.vgc")
@PropertySource("classpath:test.properties")
public class ConfigBean {
	
	private String type;
	private String provider;
}
