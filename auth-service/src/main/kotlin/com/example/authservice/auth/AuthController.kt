package com.example.authservice.auth

import com.example.authservice.database.User
import com.example.authservice.database.UserRepo
import com.example.authservice.dto.RegisterRequest
import com.example.authservice.dto.RegisterResponse
import com.example.authservice.service.RegisterService
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class AuthController(
    val registerService: RegisterService
) {
    @PostMapping("/auth/register", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun register(@RequestBody registerRequest: RegisterRequest): RegisterResponse {
        return registerService.register(registerRequest.username, registerRequest.password)
    }
    @GetMapping("/test")
    fun register( auth: Principal): String {
        return auth.name

    }

}