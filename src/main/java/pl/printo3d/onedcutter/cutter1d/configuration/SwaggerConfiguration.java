package pl.printo3d.onedcutter.cutter1d.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;

import io.swagger.annotations.AuthorizationScope;
import io.swagger.v3.oas.models.security.SecurityScheme;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
    public ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
        .title("1D Cutter API")
        .description("API swagger test")
        .version("1.0.0")
        .build();
    }

    public Docket docket()
    {
        return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .enable(true)
        .select()
        .paths(PathSelectors.any())
        .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("jwtToken", "Authorization", "header");
    }    
}