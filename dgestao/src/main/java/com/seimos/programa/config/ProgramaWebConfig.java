package com.seimos.programa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author moesio @ gmail.com
 * @date May 24, 2016 8:54:15 PM
 */
@Configuration
@ComponentScan(basePackages = { "com.seimos.programa.validator", "com.seimos.programa.dao", "com.seimos.programa.service", "com.seimos.programa.controller", })
public class ProgramaWebConfig {

}
