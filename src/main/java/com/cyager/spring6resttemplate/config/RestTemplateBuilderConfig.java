package com.cyager.spring6resttemplate.config;

import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateBuilderConfig {

    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {
        RestTemplateBuilder restTemplateBuilder = configurer.configure(new RestTemplateBuilder());
        DefaultUriBuilderFactory uriBuilderFactory =
                new DefaultUriBuilderFactory("http://localhost:8080");
        return restTemplateBuilder.uriTemplateHandler(uriBuilderFactory);
    }

//    public final String HOST_NAME = "localhost:8080";
//    public final String URL_PATH = "/api/v1/beer";
}
