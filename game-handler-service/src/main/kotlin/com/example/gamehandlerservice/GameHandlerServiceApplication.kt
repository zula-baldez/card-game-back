package com.example.gamehandlerservice

import com.example.gamehandlerservice.config.RsaKeyProperties
import io.grpc.Server
import io.grpc.ServerBuilder
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication


@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties::class)
class GameHandlerServiceApplication

fun main(args: Array<String>) {

    runApplication<GameHandlerServiceApplication>(*args)
}
