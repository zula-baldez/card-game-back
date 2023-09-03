package com.example.gamehandlerservice.service.rooms.management

import com.example.gamehandlerservice.service.game.process.RoomHandler

interface RoomManager {
    fun createRoom(name: String, hostId: Long, capacity: Int): RoomHandler
    fun deleteRoom(id : Long)
    fun getRoom(id : Long) : RoomHandler?
    fun getAllRooms() : List<RoomHandler>
}
