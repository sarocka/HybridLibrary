package com.hybridit.HybridLibrary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LibraryAuthenticationProvider libraryAuthenticationProvider;

    public SecurityConfig(LibraryAuthenticationProvider libraryAuthenticationProvider) {
        this.libraryAuthenticationProvider = libraryAuthenticationProvider;
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(libraryAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().and().authorizeRequests().antMatchers("/**").authenticated();
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2_console/**").permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}

