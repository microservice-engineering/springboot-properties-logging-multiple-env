package com.spring.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix="com.vgc")
@Data
public class MultipleProperities {
	
	private String type;
	private String provider;
}
