package com.jl.crm.web;

import com.jl.crm.services.ServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.hal.DefaultCurieProvider;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.MultipartConfigElement;

@ComponentScan
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {
    private static Class<Application> applicationClass = Application.class;

    public static void main(String[] args) {
        SpringApplication.run(applicationClass);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}

@Configuration
@EnableWebMvc
@Import(ServiceConfiguration.class)
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
class WebMvcConfiguration {

    String curieNamespace = "crm";

    @Bean
    MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }

    @Bean
    MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    DefaultCurieProvider defaultCurieProvider() {
        org.springframework.hateoas.UriTemplate template = new org.springframework.hateoas.UriTemplate(
                "http://localhost:8080/rels/{rel}");
        return new DefaultCurieProvider(curieNamespace, template);
    }
}
