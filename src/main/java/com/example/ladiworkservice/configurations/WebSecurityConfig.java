package com.example.ladiworkservice.configurations;

import com.example.ladiworkservice.exceptions.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
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
                .antMatchers("/**/login").permitAll()
                .antMatchers("/**/call/**").permitAll()
                .antMatchers("/**/call-logs/**").permitAll()
                .antMatchers("/**/data/createData").permitAll()
                .antMatchers("/**/data/create/v2").permitAll()
                .antMatchers("/**/data/update-status-data/ghsv").permitAll()
                .antMatchers("/**/data/update-status-data/ghn").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}

