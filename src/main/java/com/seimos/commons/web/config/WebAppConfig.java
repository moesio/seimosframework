package com.seimos.commons.web.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.seimos.commons.reflection.LayerGenerator;

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

	//	static {
	//		String[] domainPackages = ConfigReader.getKey(ConfigKey.datasource_packageToScan).split(",");
	//		for (String packageName : domainPackages) {
	//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
	//			try {
	//				Enumeration<URL> resources = loader.getResources(packageName.replace(".", "/"));
	//				ArrayList<File> dirs = new ArrayList<File>();
	//				while (resources.hasMoreElements()) {
	//					URL url = resources.nextElement();
	//					dirs.add(new File(url.getFile()));
	//					//					System.out.println(file);
	//				}
	//				for (File dir : dirs) {
	//					List<Class<?>> classes = findClasses(dir, packageName);
	//					for (Class<?> clazz : classes) {
	//						LayerGenerator.generateLayers(clazz);
	//					}
	//					//					generateLayers(findClasses(dir, packageName), packageName);
	//				}
	//			} catch (IOException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			} catch (ClassNotFoundException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//		}
	//	}
	//
	//	private static List<Class<?>> findClasses(File dir, String packageName) throws ClassNotFoundException {
	//		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
	//		if (!dir.exists()) {
	//			return classes;
	//		}
	//		File[] listFiles = dir.listFiles();
	//		for (File file : listFiles) {
	//			if (file.isDirectory()) {
	//				assert !file.getName().contains(".");
	//				classes.addAll(findClasses(file, packageName));
	//			} else if (file.getName().endsWith(".class")) {
	//				classes.add(Class.forName(
	//						packageName.concat(".").concat(file.getName().substring(0, file.getName().length() - 6))));
	//			}
	//		}
	//		return classes;
	//	}

	static {
		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
				false);
		String[] domainPackages = ConfigReader.getKey(ConfigKey.datasource_packageToScan).split(",");
		provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

		for (String basePackage : domainPackages) {
			Set<BeanDefinition> classes = provider.findCandidateComponents(basePackage);
			for (BeanDefinition bean : classes) {
				try {
					Class<?> clazz = Class.forName(bean.getBeanClassName());
					LayerGenerator.generateLayers(clazz);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

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

	@Bean
	public FreeMarkerConfigurer freemarkerConfigurer() {
		Properties properties = new Properties();
		properties.setProperty(freemarker.template.Configuration.AUTO_IMPORT_KEY, "/spring.ftl as spring");
		properties.setProperty(freemarker.template.Configuration.LOCALIZED_LOOKUP_KEY, "false");
		properties.setProperty(freemarker.template.Configuration.DATE_FORMAT_KEY, "yyyy-MM-dd");

		ArrayList<String> templateLoaderPaths = new ArrayList<String>();
		templateLoaderPaths.addAll(Arrays.asList(ConfigReader.getKey(ConfigKey.viewPath).split(",")));
		templateLoaderPaths.add("classpath:/META-INF/views/");

		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPaths(templateLoaderPaths.toArray(new String[templateLoaderPaths.size()]));
		configurer.setDefaultEncoding("UTF-8");
		configurer.setFreemarkerSettings(properties);

		return configurer;
	}

	private Properties getHibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty(ConfigKey.hibernate_hbm2ddl_auto.toString(),
				ConfigReader.getKey(ConfigKey.hibernate_hbm2ddl_auto));
		hibernateProperties.setProperty(ConfigKey.hibernate_dialect.toString(),
				ConfigReader.getKey(ConfigKey.hibernate_dialect));
		hibernateProperties.setProperty("hibernate.connection.release_mode", "auto");
		hibernateProperties.setProperty("hibernate.connection.datasource",
				"java:comp/env/" + ConfigReader.getKey(ConfigKey.datasource_jndi_name));

		Properties config = ConfigReader.getInstance();
		Set<Object> keys = config.keySet();
		for (Object key : keys) {
			if (key.toString().startsWith("hibernate")) {
				hibernateProperties.put(key, config.get(key));
			}
		}

		return hibernateProperties;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setPackagesToScan(ConfigReader.getKey(ConfigKey.datasource_packageToScan).split(","));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		sessionFactory.setImplicitNamingStrategy(ImplicitNamingStrategyComponentPathImpl.INSTANCE);
		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
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

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern("/webjars/**")) {
			registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		}
	}

}
