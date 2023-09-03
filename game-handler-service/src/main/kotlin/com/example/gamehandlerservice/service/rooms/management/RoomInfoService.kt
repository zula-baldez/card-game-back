package com.example.gamehandlerservice.service.rooms.management

import com.example.gamehandlerservice.database.Account
import org.springframework.stereotype.Component

@Component
class RoomInfoServiceImpl(private val roomManager: RoomManager) :  RoomInfoService {
    override fun getRoomInfo(roomId : Int, playerId: Long) : List<Account>? {
        val roomHandler = roomManager.getRoom(roomId.toLong())
        val players = roomHandler?.getAllPlayers()?.toMutableList()

        val playerToMove = players?.find { it.id == playerId }

        if (playerToMove != null) {
            players.removeAt(players.indexOf(playerToMove))
            players.add(0, playerToMove)
        }
        println(players)
        return players
    }
}

 interface RoomInfoService {
     fun getRoomInfo(roomId : Int, playerId: Long) : List<Account>?
}
