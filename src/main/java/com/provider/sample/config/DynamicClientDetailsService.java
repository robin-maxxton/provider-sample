package com.provider.sample.config;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * Created by Hermans.r on 5/13/2016.
 */
public class DynamicClientDetailsService implements ClientDetailsService {

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    /**
     * Quick example client details
     * This will be replaced by a call to a microservice just like in the custom provider
     */
    Collection<String> ccScopes = new HashSet<>();
    ccScopes.add("all");

    Collection<String> ccGrantTypes = new HashSet<>();
    ccGrantTypes.add("client_credentials");

    BaseClientDetails ccClientDetails = new BaseClientDetails();
    ccClientDetails.setClientId("username");
    ccClientDetails.setClientSecret("password");
    ccClientDetails.setScope(ccScopes);
    ccClientDetails.setAutoApproveScopes(ccScopes);
    ccClientDetails.setAuthorizedGrantTypes(ccGrantTypes);
    return ccClientDetails;
  }

}
