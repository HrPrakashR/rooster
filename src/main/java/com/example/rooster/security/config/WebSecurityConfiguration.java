package com.example.rooster.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/api/auth/login").permitAll()
                    .antMatchers("/api/period/**").authenticated()
                    .antMatchers("/api/employee/**").authenticated()
                    .antMatchers("/api/team/**").authenticated()
                    .antMatchers("/api/secret/admin").hasRole(SecurityService.OWNER_ROLE)
                    .anyRequest().authenticated();

            http
                    .logout()
                    .logoutUrl("/api/auth/logout")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
                    .permitAll();

            http
                    .httpBasic().and()
                    .csrf().disable()
                    .cors().and()
                    .formLogin().disable();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            // Make localhost:8080/h2-console accessible during development
            web
                    .ignoring()
                    .antMatchers("/h2-console/**");
        }


    }

