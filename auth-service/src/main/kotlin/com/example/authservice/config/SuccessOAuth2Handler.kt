package com.example.authservice.config

import com.example.authservice.database.User
import com.example.authservice.database.UserRepo
import com.example.authservice.jwt.TokenService
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class SuccessOAuth2Handler(val jwtTokenProvider: TokenService, private val userRepo: UserRepo) :
    AuthenticationSuccessHandler {


    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        if (authentication is OAuth2AuthenticationToken) {
            val name = authentication.principal.name
            println(name)
            val user = userRepo.findByName(name).orElseGet{
                val user : User = User(name, "vrebegdvsrtbvetbe") //todo generate password
                userRepo.save(user)
                user
            }
            // Generate the JWT token
            val jwtToken = jwtTokenProvider.generateAccessToken(user)
            response.status=302
            response.setHeader("Location", "http://localhost:9999/$jwtToken")
            println(jwtToken)
            response.setHeader("Authorization", "Bearer $jwtToken")

        }
    }
}