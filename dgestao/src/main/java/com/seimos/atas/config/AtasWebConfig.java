package com.seimos.atas.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author moesio @ gmail.com
 * @date May 11, 2016 10:54:25 PM
 */
@Configuration
@ComponentScan(basePackages = {
		"com.seimos.atas.controller", 
		"com.seimos.atas.service", 
		"com.seimos.atas.dao", 
		"com.seimos.atas.validator", 
		})
public class AtasWebConfig {

}
