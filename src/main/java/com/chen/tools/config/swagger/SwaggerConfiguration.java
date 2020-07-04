package com.chen.tools.config.swagger;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yeyc
 * 参考 https://swagger.io/docs/specification/authentication/bearer-authentication/
 */
@Configuration
@EnableSwagger2
@Profile({"dev","test","release"})
@ConfigurationProperties(prefix = "o2osys.interceptor")
public class SwaggerConfiguration {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public SwaggerProperties setSwaggerProperties() {
        return new SwaggerProperties();
    }

    private List<String> excludes = new ArrayList<>();

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

    @Bean
    public Docket authRestApi() throws IllegalAccessException {
        // 添加头部header参数
        List<Parameter> params = new ArrayList();
        params.add(new ParameterBuilder().name("X-did").description("did")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build());
        params.add(new ParameterBuilder().name("X-store").description("store")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build());
        params.add(new ParameterBuilder().name("X-uid").description("uid")
                .modelRef(new ModelRef("string")).parameterType("header").required(true).build());
        params.add(new ParameterBuilder().name("X-token").description("token")
                .modelRef(new ModelRef("string")).parameterType("header").required(true).build());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerProperties.getAuthGroupName())
                .apiInfo(authInfo())
                .select()
                //此处为controller包路径
                .apis(basePackage(swaggerProperties.getControllerPath()))
                .paths(Predicates.not(
                        Predicates.or(excludes.stream().map(item -> PathSelectors.ant(item)).collect(Collectors.toList()))
                ))
                .build()
                .globalOperationParameters(params);
    }

    @Bean
    public Docket openRestApi() throws IllegalAccessException {
        // 添加头部header参数
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerProperties.getOpenGroupName())
                .apiInfo(openInfo())
                .select()
                //此处为controller包路径
                .apis(basePackage(swaggerProperties.getControllerPath()))
                .paths(Predicates.or(excludes.stream().map(item -> PathSelectors.ant(item)).collect(Collectors.toList())))
                .build();
    }

    private ApiInfo authInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getAuthDescription())
                .version(swaggerProperties.getVersion())
                .build();
    }

    private ApiInfo openInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getOpenDescription())
                .version(swaggerProperties.getVersion())
                .build();
    }

    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(";")) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }

}
