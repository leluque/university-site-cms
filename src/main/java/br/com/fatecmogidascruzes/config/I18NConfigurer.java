package br.com.fatecmogidascruzes.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class I18NConfigurer implements WebMvcConfigurer {

	/*
	 * @Bean public MessageSource messageSource() { ResourceBundleMessageSource
	 * messageSource = new ResourceBundleMessageSource();
	 * messageSource.setBasename("i18n/messages");
	 * messageSource.setDefaultEncoding("UTF-8"); return messageSource; }
	 * 
	 */ @Bean
	public LocaleResolver localeResolver() {
		Locale locale = LocaleContextHolder.getLocale();
		if (null == locale) {
			locale = new Locale("pt", "BR");
		}

		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(locale);
		return sessionLocaleResolver;
	}

	/*
	 * @Bean public LocaleChangeInterceptor localeChangeInterceptor() {
	 * LocaleChangeInterceptor localeChangeInterceptor = new
	 * LocaleChangeInterceptor(); localeChangeInterceptor.setParamName("language");
	 * return localeChangeInterceptor; }
	 * 
	 * @Override public void addInterceptors(InterceptorRegistry registry) {
	 * registry.addInterceptor(localeChangeInterceptor()); }
	 */
}
