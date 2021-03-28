package com.mrprez.gencross.online.context;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages = "com.mrprez.gencross.online.controller")
public class GencrossSpringConfiguration {
	
	@Bean
	public MessageSource messageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasenames("languages/messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}


}
