package com.example.roomservice.controllers

import com.example.roomservice.dto.CreateRoomDTO
import com.example.roomservice.dto.RoomChangeResponse
import com.example.roomservice.service.RoomService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@Controller
class RoomController(private val roomService : RoomService) {
    @GetMapping("/get-rooms")
    fun getAvailableRooms() : List<RoomChangeResponse> {
        return ArrayList()
    }
    @MessageMapping("/create-room")
    @SendTo("/topic/new-rooms")
    fun createRoom(createRoomDTO: CreateRoomDTO, principal: Principal) : RoomChangeResponse? {
        val createRoomResponse = roomService.createRoom(createRoomDTO, principal.name.toLong())
        if(createRoomResponse?.success == true) {
            return RoomChangeResponse(createRoomResponse.capacity, createRoomResponse.name, createRoomResponse.id, createRoomResponse.hostId, createRoomResponse.count)
        }
        return null
    }
    @MessageMapping("/all-rooms")
    @SendTo("/topic/all-rooms")
    fun createRoom() : List<RoomChangeResponse> {
        return roomService.getAllRooms().map {RoomChangeResponse(it.capacity, it.name, it.id, it.hostId, it.count)  }.toList()
    }

}