package com.spacemonkeys.farmbox.config;


import com.spacemonkeys.farmbox.Models.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {

@Bean
    public Docket farmboxApi(){
    return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.spacemonkeys.farmbox"))
            .paths(PathSelectors.ant("/**"))
            .build()
            .ignoredParameterTypes(Users.class);
}

}
