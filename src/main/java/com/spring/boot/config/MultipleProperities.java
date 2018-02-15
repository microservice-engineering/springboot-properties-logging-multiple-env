package com.spring.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

//配置文件注入
@Configuration
@ConfigurationProperties(prefix="com.vgc")
@Data
public class MultipleProperities {
	
	private String type;
	private String provider;
}
