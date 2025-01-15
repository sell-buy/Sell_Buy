package com.sell_buy.sell_buy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/style/**")
                .addResourceLocations("classpath:/static/style/");
        registry.addResourceHandler("/script/**")
                .addResourceLocations("classpath:/static/script/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/webjars/");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/");
    }
}
