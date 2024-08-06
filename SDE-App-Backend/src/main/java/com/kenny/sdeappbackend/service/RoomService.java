package com.kenny.sdeappbackend.service;

import com.kenny.sdeappbackend.exception.ResourceNotFoundException;
import com.kenny.sdeappbackend.model.Room;
import com.kenny.sdeappbackend.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepo roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room roomDetails) {
        return roomRepository.findById(id).map(room -> {
            room.setName(roomDetails.getName());
            room.setCapacity(roomDetails.getCapacity());
            room.setManager(roomDetails.getManager());
            return roomRepository.save(room);
        }).orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
