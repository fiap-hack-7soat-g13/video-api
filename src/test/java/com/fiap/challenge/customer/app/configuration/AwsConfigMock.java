package com.fiap.challenge.user.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

import static org.mockito.Mockito.mock;

@Configuration
@Profile("test")
public class AwsConfigMock {

    @Bean
    public CognitoIdentityProviderClient cognitoIdentityProviderClientClient() {
        return mock(CognitoIdentityProviderClient.class);
    }

}
