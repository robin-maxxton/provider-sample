package com.provider.sample.config;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private CustomAuthenticationProvider customAuthenticationProvider;

  @Bean
  protected AuthenticationManager authenticationManager() {
    List<AuthenticationProvider> providers = new ArrayList<>();
    providers.add(customAuthenticationProvider);
    return new ProviderManager(providers);
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "foobar".toCharArray()).getKeyPair("test");
    converter.setKeyPair(keyPair);
    return converter;
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    /**
     * ############ IMPORTANT ##################
     * I want this to be dynamic
     * So on each authorization request I want to make a call to another rest application using the feign client
     * The response will be either a object or null.
     * Based on that I want the client to grant access or deny it.
     */
    clients.withClientDetails(new DynamicClientDetailsService());
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager()).accessTokenConverter(jwtAccessTokenConverter());
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    //oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    oauthServer.allowFormAuthenticationForClients();
  }

}
