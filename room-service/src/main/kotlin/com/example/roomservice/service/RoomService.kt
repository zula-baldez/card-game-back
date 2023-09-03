package com.example.roomservice.service

import com.example.grpc.roomservice.*
import com.example.grpc.roomservice.RoomServiceGrpc.RoomServiceBlockingStub
import com.example.roomservice.dto.CreateRoomDTO
import io.grpc.ManagedChannelBuilder
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service


@Service
class RoomService  {
    private val channel = ManagedChannelBuilder.forAddress("localhost", 9090)
        .usePlaintext()
        .build()
    private val roomServiceBlockingStub: RoomServiceBlockingStub = RoomServiceGrpc.newBlockingStub(channel)


    @GrpcClient("room-service")
    fun createRoom(createRoomDTO: CreateRoomDTO, hostId: Long) : RoomResponse? {
        val createRoomRequest: CreateRoomRequest = CreateRoomRequest.newBuilder().setCapacity(createRoomDTO.capacity).setName(createRoomDTO.name).setHostId(hostId).build()
        return roomServiceBlockingStub.createRoom(createRoomRequest)
    }
    @GrpcClient("room-service")
    fun getAllRooms() : List<RoomResponse> {
        val voidMessage: VoidMessage = VoidMessage.newBuilder().build()
        return roomServiceBlockingStub.getAllRooms(voidMessage).roomsList
    }
}
