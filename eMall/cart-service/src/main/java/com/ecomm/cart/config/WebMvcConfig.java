package com.ecomm.cart.config;

import com.ecomm.cart.interceptor.UserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register the UserInfoInterceptor to intercept all requests
        registry.addInterceptor(new UserInfoInterceptor())
                .addPathPatterns("/**"); // Apply to all paths
    }
}
