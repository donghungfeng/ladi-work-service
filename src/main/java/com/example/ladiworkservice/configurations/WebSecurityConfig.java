package com.example.ladiworkservice.configurations;

import com.example.ladiworkservice.exceptions.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] AUTH_WHITELIST = {
                "/swagger-resources/**",
                "/**/swagger-ui/**",
                "/v2/api-docs",
                "/webjars/**"
        };

        http.cors().and().csrf().disable();

        http.
                exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/**/unit/**").permitAll()
                .antMatchers("/**/ip").permitAll()
                .antMatchers("/**/log_work/**").permitAll()
                .antMatchers("/**/location/**").permitAll()
                .anyRequest().authenticated();
    }
}
