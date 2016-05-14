package com.seimos.dgestao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author moesio @ gmail.com
 * @date May 11, 2016 10:54:25 PM
 */
@Configuration
@ComponentScan(basePackages = {
		"com.seimos.dgestao.controller", 
		"com.seimos.dgestao.service", 
		"com.seimos.dgestao.dao", 
		"com.seimos.dgestao.validator", 
		"com.seimos.commons.web.exception",
		})
public class WebConfig {

}
