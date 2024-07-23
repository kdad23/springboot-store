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
                .pathsToMatch("/angular/users/**")
                .build();
    }
    @Bean
    public GroupedOpenApi toDoApi()
    {
        return GroupedOpenApi.builder()
                .group("待辦事項管理")
                .pathsToMatch("/angular/note/**")
                .pathsToMatch("/angular/notes/**")
                .build();
    }

    @Bean
    public OpenAPI docsOpenApi()
    {
        return new OpenAPI()
                .info(new Info().title("待辦事項 API")
                        .description("待辦事項應用")
                        .version("v0.0.1")
        .license(new License().name("Apache").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation().description("參考文件")
                        .url(""));
    }

}
