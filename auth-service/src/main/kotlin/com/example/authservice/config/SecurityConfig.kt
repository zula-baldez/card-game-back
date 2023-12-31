package com.example.authservice.config

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.client.RestTemplate
import java.util.*


@Configuration
@EnableWebSecurity
class SecurityConfig(
    val rsaKeyProperties: RsaKeyProperties,
) {
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun accessTokenResponseClient(customTokenResponseConverter: CustomTokenResponseConverter): OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {
        val accessTokenResponseClient = DefaultAuthorizationCodeTokenResponseClient()
        val tokenResponseHttpMessageConverter = OAuth2AccessTokenResponseHttpMessageConverter()
        tokenResponseHttpMessageConverter.setAccessTokenResponseConverter(customTokenResponseConverter)
        val restTemplate = RestTemplate(
            listOf(
                FormHttpMessageConverter(), tokenResponseHttpMessageConverter
            )
        )
        restTemplate.errorHandler = OAuth2ErrorResponseErrorHandler()
        accessTokenResponseClient.setRestOperations(restTemplate)
        return accessTokenResponseClient
    }


    @Bean
    @Throws(Exception::class)
    fun filterChain(
        customService: CustomOAuthUserService,
        accessTokenResponseClient: OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>,
        http: HttpSecurity,
        oauth2SuccessHandler: SuccessOAuth2Handler,
        successLoginPasswordHandler: SuccessLoginPasswordHandler
    ): SecurityFilterChain {


        http
            .sessionManagement { i -> i.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { i ->
                i.requestMatchers(AntPathRequestMatcher("/auth/register")).permitAll().anyRequest().authenticated()
            }
            .csrf { i -> i.disable() }
            .oauth2Login { i ->
                i.tokenEndpoint { j -> j.accessTokenResponseClient(accessTokenResponseClient) }
                    .userInfoEndpoint { j -> j.userService(customService) }
                    .successHandler(oauth2SuccessHandler)
            }.formLogin {j ->
                j.successHandler(successLoginPasswordHandler)
            }
            .oauth2ResourceServer { j -> j.jwt {} }



        return http.build()
    }


    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey).build()
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(rsaKeyProperties.publicKey).privateKey(rsaKeyProperties.privateKey).build()
        val jwks = ImmutableJWKSet<SecurityContext>(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }
}
