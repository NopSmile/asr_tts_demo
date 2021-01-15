package com.tx.filedown.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //外部访问路径映射到本地磁盘路径
        //registry.addResourceHandler("/voice/**").addResourceLocations("file:/home/asrdata/");
        registry.addResourceHandler("/voice/**").addResourceLocations("file:D:\\data\\asrdata\\");
    }
}
