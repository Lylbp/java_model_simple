package com.lylbp.manager.swagger;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
/**
 * swagger配置
 *
 * @author weiwenbin
 */
@EnableSwagger2WebMvc
@Configuration
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("默认", "默认", "韦文彬"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lylbp.project.controller"))
                .build()
                .groupName("默认")
                .pathMapping("/");
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.activiti", name = "database-schema-update", havingValue = "true")
    public Docket activity() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("activityDemo", "activityDemo", "韦文彬"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lylbp.manager.activity.demo.controller"))
                .build()
                .groupName("activityDemo")
                .pathMapping("/");
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.data.elasticsearch.repositories", name = "enabled", havingValue = "true")
    public Docket es() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("esDemo", "esDemo", "韦文彬"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lylbp.manager.elasticsearch.demo.controller"))
                .build()
                .groupName("esDemo")
                .pathMapping("/");
    }

    @Bean
    @ConditionalOnProperty(prefix = "minio", name = "enabled")
    public Docket minio() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("minioDemo", "minioDemo", "韦文彬"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lylbp.manager.minio.demo.controller"))
                .build()
                .groupName("minioDemo")
                .pathMapping("/");
    }

    @Bean
    @ConditionalOnProperty(prefix = "hbase", name = "enabled", havingValue = "true")
    public Docket hbaseDemo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("hbaseDemo", "hbaseDemo", "韦文彬"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lylbp.manager.hbase.demo.controller"))
                .build()
                .groupName("hbaseDemo")
                .pathMapping("/");
    }

    @Bean
    @ConditionalOnProperty(prefix = "jpush", name = "enabled", havingValue = "true")
    public Docket jpushDemo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("jpushDemo", "JpushDemo", "韦文彬"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lylbp.manager.jpush.demo.controller"))
                .build()
                .groupName("jpushDemo")
                .pathMapping("/");
    }

    @Bean
    @ConditionalOnProperty(prefix = "kafka", name = "bootstrap-servers")
    public Docket kafkaDemo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("kafkaDemo", "kafkaDemo", "韦文彬"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lylbp.manager.kafka.demo.controller"))
                .build()
                .groupName("kafkaDemo")
                .pathMapping("/");
    }

    private ApiInfo apiInfo(String name, String description, String contactName) {
        Contact contact = new Contact(contactName, "", "");
        return new ApiInfoBuilder().title(name).description(description).contact(contact).build();
    }
}
