package com.cannon.nop.interfaces.config;

import com.cannon.nop.interfaces.config.filter.CORSFilter;
import com.cannon.nop.interfaces.config.filter.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilterRegistration(JwtAuthenticationFilter filter) {
        FilterRegistrationBean<JwtAuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
        registration.addUrlPatterns(ApiPaths.BASE_URL+"/organizer/*/*");  // 보호할 URL 패턴 지정
        registration.setOrder(1);
        return registration;
    }
    @Bean
    public FilterRegistrationBean<CORSFilter> corsFilterRegistration(CORSFilter filter) {
        FilterRegistrationBean<CORSFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setOrder(0);
        return registration;
    }
}
