package org.example.securitylogintest.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

//Springdoc OpenAPI에서 전역적으로 설정할 수 있는 기본적인 정보를 설정하는 데 사용
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Security Login Test",
                description = "Security and OAuth2 docs test",
                version = "v1"
        )
)
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(info());
    }

    private Info info() {
        return new Info()
                .title("Security Login Test")
                .description("Security and OAuth2 docs test")
                .version("v1");
    }
}
