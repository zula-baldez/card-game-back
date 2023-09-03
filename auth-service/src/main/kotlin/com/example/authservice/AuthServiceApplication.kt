package com.example.authservice

import com.example.authservice.config.RsaKeyProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties::class)
class AuthServiceApplication
//todo добавить гугл
fun main(args: Array<String>) {
    runApplication<AuthServiceApplication>(*args)
}

