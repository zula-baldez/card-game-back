package com.example.roomservice

import com.example.roomservice.security.RsaKeyProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties::class)
class RoomServiceApplication

fun main(args: Array<String>) {
    runApplication<RoomServiceApplication>(*args)
}
