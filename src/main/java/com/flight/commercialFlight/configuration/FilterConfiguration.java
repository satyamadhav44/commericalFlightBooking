package com.flight.commercialFlight.configuration;

import com.flight.commercialFlight.utils.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<CustomFilter> customFilterRegistrationBean() {
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();

        // Set the filter instance
        registrationBean.setFilter(new CustomFilter());

        // Define URL patterns to which the filter should be applied
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
}
