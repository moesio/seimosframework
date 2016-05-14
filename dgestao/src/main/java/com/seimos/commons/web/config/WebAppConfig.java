package com.seimos.commons.web.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * @author moesio @ gmail.com
 * @date Oct 20, 2014 12:12:37 AM
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
// TODO Inject package from config.properties
//@PropertySource(value = "classpath:config.properties")
public class WebAppConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ArrayList<ViewResolver> resolvers = new ArrayList<ViewResolver>();

		FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
		freeMarkerViewResolver.setCache(false);
		freeMarkerViewResolver.setSuffix(".ftl");
		freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");
		freeMarkerViewResolver.setViewClass(FreeMarkerView.class);
		resolvers.add(freeMarkerViewResolver);

		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(resolvers);
		resolver.setContentNegotiationManager(manager);

		return resolver;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureDefaultServletHandling(org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer)
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	public static void main(String[] args) {
		new WebAppConfig().freemarkerConfigurer();
	}
	
	@Bean
	public FreeMarkerConfigurer freemarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		
		ArrayList<String> templateLoaderPaths = new ArrayList<String>();
		templateLoaderPaths.add("classpath:/META-INF/views/");
		templateLoaderPaths.addAll(Arrays.asList(ConfigReader.getKey(ConfigKey.viewPath).split(",")));
		
		configurer.setTemplateLoaderPaths(templateLoaderPaths.toArray(new String[templateLoaderPaths.size()]));
		configurer.setDefaultEncoding("UTF-8");
		Properties properties = new Properties();
		properties.setProperty(freemarker.template.Configuration.AUTO_IMPORT_KEY, "/spring.ftl as spring");
		configurer.setFreemarkerSettings(properties);

		return configurer;
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(ConfigReader.getKey(ConfigKey.datasource_jdbc_driverClassName));
		dataSource.setUrl(ConfigReader.getKey(ConfigKey.datasource_jdbc_url));
		dataSource.setUsername(ConfigReader.getKey(ConfigKey.datasource_jdbc_username));
		dataSource.setPassword(ConfigReader.getKey(ConfigKey.datasource_jdbc_password));
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty(ConfigKey.hibernate_hbm2ddl_auto.toString(), ConfigReader.getKey(ConfigKey.hibernate_hbm2ddl_auto));
		hibernateProperties.setProperty(ConfigKey.hibernate_dialect.toString(), ConfigReader.getKey(ConfigKey.hibernate_dialect));
		hibernateProperties.setProperty("hibernate.connection.release_mode", "auto");

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(ConfigReader.getKey(ConfigKey.datasource_packageToScan).split(","));
		sessionFactory.setHibernateProperties(hibernateProperties);

		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {

		String defaultLocale = ConfigReader.get("locale");
		if (defaultLocale != null) {
			Locale.setDefault(new Locale(defaultLocale));
		}

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setCacheSeconds(1);

		return messageSource;
	}

}
