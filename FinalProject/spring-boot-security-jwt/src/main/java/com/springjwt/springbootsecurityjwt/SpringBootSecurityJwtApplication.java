package com.springjwt.springbootsecurityjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableFeignClients("com.springjwt.springbootsecurityjwt.feignclients")
@EnableEurekaClient
@EnableSwagger2
public class SpringBootSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}
	/*@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.springjwt.springbootsecurityjwt")).build();
	}*/
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}


	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"My User Service API",
				"This API is used for Authentication and Authorization purpose.",
				"1.0",
				"Terms of service",
				new Contact("Bhawna Raghuvanshi", "www.blogpost.com", "bragi@allstate.com"),
				"License of API",
				"API license URL",
				Collections.emptyList());
	}

}
