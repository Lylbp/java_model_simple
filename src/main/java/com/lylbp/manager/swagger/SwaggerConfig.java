package com.lylbp.manager.swagger;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

/**
 * swagger配置
 *
 * @author weiwenbin
 */
@EnableSwagger2
@Configuration
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Resource
    SwaggerProperties swaggerProperties;

    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("默认", "默认", "1.0", "韦文彬"))
                .enable(swaggerProperties.getEnabled())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lylbp.project.controller"))
                .build()
                .groupName("默认")
                .pathMapping("/");
    }

    @Bean
    public Docket bg() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("后台", "后台", "1.0", "韦文彬"))
                .enable(swaggerProperties.getEnabled())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lylbp.project.controller.bg"))
                .build()
                .groupName("后台")
                .pathMapping("/");
    }

    @Bean
    public Docket activity() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("activityDemo", "activityDemo", "1.0", "韦文彬"))
                .enable(swaggerProperties.getEnabled())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lylbp.manager.activity.demo.controller"))
                .build()
                .groupName("activityDemo")
                .pathMapping("/");
    }

    @Bean
    public Docket minio() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("minioDemo", "minioDemo", "1.0", "韦文彬"))
                .enable(swaggerProperties.getEnabled())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lylbp.manager.minio.demo.controller"))
                .build()
                .groupName("minioDemo")
                .pathMapping("/");
    }

    @Bean
    public Docket test() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("测试", "测试", "1.0", "韦文彬"))
                .enable(swaggerProperties.getEnabled())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lylbp.project.controller.test"))
                .build()
                .groupName("测试")
                .pathMapping("/");
    }

    private ApiInfo apiInfo(String name, String description, String version, String contactName) {
        Contact contact = new Contact(contactName, "", "");
        return new ApiInfoBuilder().title(name).description(description).version(version).contact(contact).build();
    }
}
