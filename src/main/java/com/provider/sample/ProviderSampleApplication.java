package com.provider.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Provider Sample Application
 */
@SpringBootApplication
@EnableAuthorizationServer
@SessionAttributes("authorizationRequest")
public class ProviderSampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProviderSampleApplication.class, args);
  }

}
