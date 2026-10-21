package com.justice.hms.Repository;

import com.justice.hms.Model.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT r FROM Room r WHERE r.occupiedSpaces < r.capacity")
    List<Room> findAvailableRooms();

    List<Room> findByHostelId(Integer hostelId);

    List<Room> findByHostelIdOrderByIdAsc(Integer hostelId);

    boolean existsByIdAndHostelId(Integer roomId, Integer hostelId);
}