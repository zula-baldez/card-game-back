package com.example.gamehandlerservice.controllers

import com.example.gamehandlerservice.database.Account
import com.example.gamehandlerservice.service.rooms.management.RoomInfoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.*

@RestController
class RoomInfoController(private val roomInfoService: RoomInfoService) {
    @GetMapping("/all-players")
    fun getPlayersInRoom(
        @RequestParam roomId: Int,
        principal: Principal
    ): List<Account>? {
        val id = principal.name.toLong()
        println(id)
        return roomInfoService.getRoomInfo(roomId, id)

    }
}