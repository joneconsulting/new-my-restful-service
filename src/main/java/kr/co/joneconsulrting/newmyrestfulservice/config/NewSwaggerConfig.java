package kr.co.joneconsulrting.newmyrestfulservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "My Restful Service API 명세서",
                description = "Spring Boot로 개발하는 RESTful API 명세서 입니다.",
                version = "v1.0.0"))
@Configuration
@RequiredArgsConstructor
public class NewSwaggerConfig {

    @Bean
    public GroupedOpenApi customTestOpenAPi() {
        String[] paths = {"/users/**", "/admin/**"};

        return GroupedOpenApi
                .builder()
                .group("일반 사용자와 관리자를 위한 User 도메인에 대한 API")
                .pathsToMatch(paths)
                .build();
    }
}