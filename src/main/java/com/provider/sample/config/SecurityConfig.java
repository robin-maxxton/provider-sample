package com.provider.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Hermans.r on 5/13/2016.
 */
@Configuration
@Order(-2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  protected CustomAuthenticationProvider customAuthenticationProvider() {
    return new CustomAuthenticationProvider();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin().loginPage("/login").permitAll().and().requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access").and().authorizeRequests().anyRequest().authenticated();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(customAuthenticationProvider());
  }

}
