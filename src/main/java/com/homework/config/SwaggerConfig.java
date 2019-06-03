package com.homework.config;

import com.homework.controller.ApiController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@PropertySource("classpath:application.properties")
@ComponentScan(basePackageClasses = ApiController.class)
@Configuration
public class SwaggerConfig {

    private static final String SWAGGER_API_VERSION = "1.0";
    private static final String LICENSE_TEXT = "License";
    private static final String TITLE = "Books Rest API";
    private static final String DESCRIPTION = "RESTful API for Books";

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(TITLE)
                .license(LICENSE_TEXT)
                .description(DESCRIPTION)
                .version(SWAGGER_API_VERSION)
                .build();
    }

    @Bean
    public Docket customerApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("customer")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex("/customer.*"))
                .build();
    }

    @Bean
    public Docket booksApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("books")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex("/books.*"))
                .build();
    }

}
