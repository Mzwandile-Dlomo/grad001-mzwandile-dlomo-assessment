package com.enviro.assessment.grad001.mzwandiledlomo.grad_assessment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Grad Assessment API")
                        .version("1.0")
                        .description("API documentation for Grad Assessment project")
                        .termsOfService("http://example.com/terms")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Mzwandile Dlomo")
                                .email("mzwandile@example.com")
                                .url("http://example.com/contact"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("MIT")
                                .url("http://example.com/license")));
    }
}

