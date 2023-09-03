package com.example.roomservice.security

import org.springframework.boot.context.properties.ConfigurationProperties
import java.security.interfaces.RSAPublicKey

@ConfigurationProperties("rsa")
class RsaKeyProperties(val publicKey : RSAPublicKey)