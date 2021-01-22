package com.br.sanches.pontointeligente.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Classe para que seja ignorada a segurança do spring.
 * @author cleber
 *
 */
//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig{ //extends WebSecurityConfigurerAdapter {
 
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //...
    }
 
   // @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/").permitAll();
    }
}