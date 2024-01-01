package com.game.slotmachine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class TokenService {
    @Autowired
    private OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;
    String getToken(){
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId("11")
                .principal("slotserver")
                .build();

        OAuth2AuthorizedClient client = oAuth2AuthorizedClientManager.authorize(request);
        System.out.println(client.getAccessToken().getTokenValue());
        return client.getAccessToken().getTokenValue();
    }

    public String getAuthTokenFromRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }
}
