package com.lvds.auth.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("forward:/login.jsp");
	}

	@Bean
	public ContentNegotiatingViewResolver contentViewResolver() throws Exception {
		final ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
		contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);

		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");

		final MappingJackson2JsonView defaultView = new MappingJackson2JsonView();
		defaultView.setExtractValueFromSingleKeyModel(true);

		final ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
		contentViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
		contentViewResolver.setViewResolvers(Arrays.<ViewResolver> asList(viewResolver));
		contentViewResolver.setDefaultViews(Arrays.<View>asList(defaultView));
		return contentViewResolver;
	}
}
