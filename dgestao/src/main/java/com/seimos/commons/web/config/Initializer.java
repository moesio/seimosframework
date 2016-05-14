package com.seimos.commons.web.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author moesio @ gmail.com
 * @date Oct 19, 2014 11:26:17 PM
 */
public class Initializer implements WebApplicationInitializer {

	/* (non-Javadoc)
	 * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
	 */
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("com.seimos.commons.web.config,".concat(ConfigReader.getKey(ConfigKey.configPackage)));
		context.setDisplayName(ConfigReader.getKey(ConfigKey.displayName));
		servletContext.addListener(new ContextLoaderListener(context));

		Dynamic dispatcherServlet = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
		dispatcherServlet.setLoadOnStartup(1);
		dispatcherServlet.addMapping("/");

		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncoding", new CharacterEncodingFilter());
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, true, "*");

		FilterRegistration.Dynamic sitemeshFilter = servletContext.addFilter("sitemesh", new ConfigurableSiteMeshFilter() {
			/* (non-Javadoc)
			 * @see org.sitemesh.config.ConfigurableSiteMeshFilter#applyCustomConfiguration(org.sitemesh.builder.SiteMeshFilterBuilder)
			 */
			@Override
			protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
				// TODO Inject from config.properties
				builder.addDecoratorPath("/*", ConfigReader.getKey(ConfigKey.decorator)).addExcludedPath("/index.*");
			}
		});
		sitemeshFilter.addMappingForServletNames(null, true, "*");
	}

}
