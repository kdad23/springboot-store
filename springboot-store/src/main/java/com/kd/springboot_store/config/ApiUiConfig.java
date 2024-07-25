package com.kd.springboot_store.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiUiConfig
{
    @Bean
    public GroupedOpenApi userApi()
    {
        return GroupedOpenApi.builder()
                .group("用戶管理")
                .pathsToMatch("/api/users/register")
                .pathsToMatch("/api/users/login")
                .build();
    }
    @Bean
    public GroupedOpenApi productApi()
    {
        return GroupedOpenApi.builder()
                .group("產品管理")
                .pathsToMatch("/api/products/**")
                .pathsToMatch("/api/**")
                .build();
    }


    @Bean
    public GroupedOpenApi orderApi()
    {
        return GroupedOpenApi.builder()
                .group("訂單管理")
                .pathsToMatch("/api/users/**")
                .build();
    }


    @Bean
    public OpenAPI docsOpenApi()
    {
        return new OpenAPI()
                .info(new Info().title("電商API")
                        .description("電商應用")
                        .version("v0.0.1")
        .license(new License().name("Apache").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation().description("參考文件")
                        .url(""));
    }

}
