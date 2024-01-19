package com.game.slotmachine.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
//import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwksuri}")
    String jwksUri;
    @Value("${tokenuri}")
    String tokenUri;
    @Value("${clienturi}")
    String clientUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http.cors((c)->{
//            CorsConfigurationSource source = s ->{
//                CorsConfiguration cc = new CorsConfiguration();
//                cc.setAllowCredentials(true);
//                cc.setAllowedOrigins(List.of("http://localhost:3006",clientUri));
//                cc.setAllowedHeaders(List.of("*"));
//                cc.setAllowedMethods(List.of("*"));
//                return cc;
//            };
//            c.configurationSource(source);
//        });
        http.cors(AbstractHttpConfigurer::disable);
        System.out.println(clientUri+" "+jwksUri);
        http.oauth2ResourceServer(
                r -> r.jwt((j)-> {
                            j.jwkSetUri(jwksUri);
                            j.jwtAuthenticationConverter(new CustomJwtAuthenticationTokenConverter());
                        }
                )

        );
        //http.oauth2Client();

        http.authorizeHttpRequests((a) -> {
            a.requestMatchers("/ws").permitAll();
            a.requestMatchers("/test").permitAll();
            a.requestMatchers("/ws/**").permitAll();
            a.requestMatchers("/sse/*").permitAll();
            //a.requestMatchers("/ticket").permitAll();
            //a.requestMatchers("/queue").permitAll();
            //a.requestMatchers("/result").permitAll();
            //a.anyRequest().permitAll();
            a.anyRequest().authenticated();
        });
        //http.cors().disable().build();
        return http.build();
    }

//    @Bean
//    public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(
//            ClientRegistrationRepository clientRegistrationRepository,
//            OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository
//    ) {
//        OAuth2AuthorizedClientProvider provider =
//                OAuth2AuthorizedClientProviderBuilder.builder()
//                        .authorizationCode()
//                        .refreshToken()
//                        .clientCredentials()
//                        .build();
//
//        DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager
//                = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, auth2AuthorizedClientRepository);
//        defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
//
//        return defaultOAuth2AuthorizedClientManager;
//    }
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        ClientRegistration c1 = ClientRegistration.withRegistrationId("11")
//                .clientId("slotserver")
//                .clientSecret("secret")
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .tokenUri(tokenUri)
//                .scope(OidcScopes.OPENID)
//                .build();
//
//        InMemoryClientRegistrationRepository clientRegistrationRepository =
//                new InMemoryClientRegistrationRepository(c1);
//
//        return clientRegistrationRepository;
//    }
}
