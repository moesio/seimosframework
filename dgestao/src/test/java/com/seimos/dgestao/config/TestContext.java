package com.seimos.dgestao.config;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.seimos.dgestao.service.GeneroService;
 
@Configuration
public class TestContext {
 
//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
// 
//        messageSource.setBasename("i18n/messages");
//        messageSource.setUseCodeAsDefaultMessage(true);
// 
//        return messageSource;
//    }
// 
    @Bean
    public GeneroService generoService() {
        return Mockito.mock(GeneroService.class);
    }
}