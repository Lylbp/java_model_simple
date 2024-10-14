package com.lylbp.core.configure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lylbp.core.configure.jackson.DynamicBeanSerializerModifier;
import com.lylbp.core.configure.jackson.JacksonObjectMapper;
import com.lylbp.core.interceptor.ApiInterceptor;
import com.lylbp.core.interceptor.NewCrossDomainInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nonnull;
import java.util.List;


/**
 * ProjectWebMvcConfigurer
 *
 * @author weiwenbin
 * @date 2020-03-12 16:49
 */
@Slf4j
@Configuration
public class ProjectWebMvcConfigurer implements WebMvcConfigurer {
    @Bean
    public RequestContextListener list() {
        return new RequestContextListener();
    }

    @Bean
    public RequestContextFilter requestContextFilter() {
        return new RequestContextFilter();
    }

    @Bean
    public ApiInterceptor getApiInterceptor() {
        return new ApiInterceptor();
    }

    @Bean
    public NewCrossDomainInterceptor getNewCrossDomainInterceptor() {
        return new NewCrossDomainInterceptor();
    }


    /**
     * 多个拦截器组成一个拦截器链
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     *
     * @param registry 拦截器注册对象
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getNewCrossDomainInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(getApiInterceptor())
                .addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/modeler/**")
                .addResourceLocations("classpath:/static/modeler/");
    }

    @Override
    public void extendMessageConverters(@Nonnull List<HttpMessageConverter<?>> converters) {
        converters.stream().filter(c -> c instanceof MappingJackson2HttpMessageConverter)
                .map(c -> (MappingJackson2HttpMessageConverter) c)
                .forEach(c -> {
                    ObjectMapper mapper = new JacksonObjectMapper(c.getObjectMapper());
                    JsonInclude.Include valueInclusion = mapper.getSerializationConfig().getDefaultPropertyInclusion().getValueInclusion();
                    if (valueInclusion == JsonInclude.Include.ALWAYS) {
                        // 为mapper注册一个带有SerializerModifier的Factory，此modifier主要做的事情为：当序列化类型为array，list、set时，当值为空时，序列化成[]
                        mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new DynamicBeanSerializerModifier()));
                        c.setObjectMapper(mapper);
                    }
                });
    }
}
