package com.justice.hms.Repository;

import com.justice.hms.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Optional<Booking> findByStudentId(Integer studentId);

    Optional<Booking> findByStudentEmail(String email);

    boolean existsByStudentId(Integer studentId);

    boolean existsByStudentEmail(String email);

    boolean existsByRoomId(Integer roomId);

    boolean existsByRoom_Hostel_Id(Integer hostelId);
}