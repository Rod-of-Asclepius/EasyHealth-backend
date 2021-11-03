package com.roa.easyhealth.security;

import com.roa.easyhealth.exception.MyException;
import generator.service.UserService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @SneakyThrows
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        throw new MyException(httpServletRequest.getRequestURI()+"    请求失败，请确保参数无误");
                    }
                })
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole("admin")
                .antMatchers("/user/**").hasAnyRole("user")
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout")
                .addLogoutHandler(new TokenLogoutHandler(userService)).and()
                .addFilter(new TokenLoginFilter(authenticationManager(), userService))
                .addFilter(new TokenAuthFilter(authenticationManager(),userService)).httpBasic()
        ;
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/register")
                .antMatchers("/signVerify")
                .antMatchers("**.jpg")
                .antMatchers("**.ico")
                .antMatchers("/validateCode")
                .antMatchers("/t_pay")
                .antMatchers("/error")
                .antMatchers("/img/**")
                .antMatchers("/common/**");
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");// TODO: 上线后得改成对应的域名
        configuration.addAllowedMethod("*");//修改为添加而不是设置
        configuration.addAllowedHeader("*");//这里很重要，起码需要允许 Access-Control-Allow-Origin
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(60*60*60L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
