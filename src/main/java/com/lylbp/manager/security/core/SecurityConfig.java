package com.lylbp.manager.security.core;

import com.lylbp.manager.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * springsecurity 配置文件
 *
 * @author weiwenbin
 * @date 2020/7/1 上午11:18
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    SecurityProperties securityProperties;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 忽略拦截url或静态资源文件夹 - web.ignoring(): 会直接过滤该url - 将不会经过Spring Security过滤器链
     * http.permitAll(): 不会绕开springsecurity验证，相当于是允许该路径通过
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        if (!securityProperties.getEnabled()) {
            web.ignoring().antMatchers("/**");
        } else {
            securityProperties.getAllowStatic().forEach(allowStatic -> web.ignoring().antMatchers(allowStatic));
        }
    }

    /**
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (!securityProperties.getEnabled()) {
            http
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic();
        } else {
            //默认配置一个Bean Name为corsConfigurationSource
            http.cors().and();
            // 由于使用的是JWT，我们这里不需要csrf
            http.csrf().disable();
            /**
             *   基于token，所以不需要session
             *   ALWAYS 总是会新建一个Session。
             *   NEVER 不会新建HttpSession，但是如果有Session存在，就会使用它。
             *   IF_REQUIRED 如果有要求的话，会新建一个Session。
             *   STATELESS 这个是我们用的，不会新建，也不会使用一个HttpSession。
             *   所有的Rest服务一定要设置为无状态，以提升操作性能
             */
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

            securityProperties.getAllowApi().forEach(allowApi -> {
                try {
                    http.authorizeRequests().antMatchers(allowApi).permitAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            //前面没有匹配上的请求，全部需要认证
            http.authorizeRequests().anyRequest().authenticated().and();
            // 禁用缓存
            http.headers().cacheControl();
            //token过滤器
            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            /**
             * 添加自定义异常入口，处理accessdeine异常
             * AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
             * AccessDeniedHandler 用来解决认证过的用户访问无权限资源时的异常
             */
            http.exceptionHandling().authenticationEntryPoint(new ProjectAuthenticationEntryPoint());
            http.exceptionHandling().accessDeniedHandler(new ProjectAccessDeniedHandler());
        }
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
