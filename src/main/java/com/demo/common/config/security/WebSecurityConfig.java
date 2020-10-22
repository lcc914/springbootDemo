package com.demo.common.config.security;

/**
 * Created by IntelliJ IDEA.
 * Date: 2020/9/23
 *
 * @author: lcc
 */

import com.demo.common.handler.JwtAccessDeniedHandler;
import com.demo.common.handler.JwtAuthenticationFailureHandler;
import com.demo.common.handler.JwtAuthenticationSuccessHandler;
import com.demo.common.handler.JwtLogoutSuccessHandler;
import com.demo.filter.JwtAuthLoginFilter;
import com.demo.filter.JwtAuthorizationFilter;
import com.demo.service.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Spring Security配置类
 *
 * @author lcc
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 未登陆时返回 JSON 格式的数据给前端（否则为 html）
     */
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    /**
     * 登录成功返回的 JSON 格式数据给前端（否则为 html）
     */
    @Autowired
    private JwtAuthenticationSuccessHandler authenticationSuccessHandler;
    /**
     * 登录失败返回的 JSON 格式数据给前端（否则为 html）
     */
    @Autowired
    private JwtAuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）
     */
    @Autowired
    private JwtLogoutSuccessHandler logoutSuccessHandler;
    /**
     * 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）
     */
    @Autowired
    private JwtAccessDeniedHandler accessDeniedHandler;


    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        /**
         * BCryptPasswordEncoder：相同的密码明文每次生成的密文都不同，安全性更高
         */
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * 用户认证配置
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 指定用户认证时，默认从哪里获取认证用户信息
         */
        auth.userDetailsService(userDetailsService())
                //配置加密方式
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 静态资源配置
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //swagger2所需要用到的静态资源，允许访问
        web.ignoring().antMatchers("/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html",
                "static/**");
    }


    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        httpSecurity.cors().and()
                //禁用 csrf
                .csrf().disable()
                // 授权异常
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()


                .authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 登录URL
                .antMatchers("/user/login").permitAll()
                // swagger
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                .antMatchers("/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "static/**").permitAll()

//                .antMatchers("/**").permitAll()
                // 其他所有请求需要身份认证
                .anyRequest().authenticated();
        // 退出登录处理器
        httpSecurity.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        //使用自定义的 Token过滤器 验证请求的Token是否合法
        // 开启登录认证流程过滤器，如果使用LoginController的login接口, 需要注释掉此过滤器，根据使用习惯二选一即可
        httpSecurity.addFilterBefore(new JwtAuthLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        // 访问控制时登录状态检查过滤器
        httpSecurity.addFilterBefore(new JwtAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.logout().logoutUrl("/logout");
    }


    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}