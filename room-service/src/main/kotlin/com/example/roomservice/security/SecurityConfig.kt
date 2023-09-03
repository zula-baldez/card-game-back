package com.example.roomservice.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(val rsaKeyProperties: RsaKeyProperties) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth -> auth.anyRequest().permitAll() }
            .oauth2ResourceServer { oauth2ResourceServer ->
                oauth2ResourceServer.jwt { token ->
                    token.decoder(jwtDecoder())
                }
            }.build()

    }


    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey).build()
    }


}