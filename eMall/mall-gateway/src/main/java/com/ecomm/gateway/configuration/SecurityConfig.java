package com.ecomm.gateway.configuration;

import java.security.KeyPair;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KeyPair keyPair(JwtProperties properties) {
        try {
            // Debug: Print the file existence and location
//            System.out.println("Keystore location: " + properties.getLocation().getURL());
//            System.out.println("Keystore exists: " + properties.getLocation().exists());
//            System.out.println("Keystore readable: " + properties.getLocation().isReadable());

            KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(
                    properties.getLocation(),
                    properties.getPassword().toCharArray());

            return keyStoreKeyFactory.getKeyPair(
                properties.getAlias(),
                properties.getPassword().toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to load keystore: " + e.getMessage(), e);
        }
    }
}