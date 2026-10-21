package com.justice.hms.Service;

import com.justice.hms.Model.Booking;
import com.justice.hms.Model.Room;
import com.justice.hms.Model.Student;
import com.justice.hms.Repository.BookingRepository;
import com.justice.hms.Repository.RoomRepository;
import com.justice.hms.Repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class BookingService {

        private final BookingRepository bookingRepository;
        private final RoomRepository roomRepository;
        private final StudentRepository studentRepository;

        public BookingService(
                BookingRepository bookingRepository,
                RoomRepository roomRepository,
                StudentRepository studentRepository) {

                this.bookingRepository = bookingRepository;
                this.roomRepository = roomRepository;
                this.studentRepository = studentRepository;
        }

        @Transactional
        public Booking bookRoom(
                String studentEmail,
                Integer roomId) {

        Student student = studentRepository
                .findByEmail(studentEmail)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student not found"
                        ));

        if (bookingRepository.existsByStudentEmail(studentEmail)) {

                throw new IllegalArgumentException(
                        "Student already has a room booking"
                );
        }

        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Room not found"
                        ));

        if (room.getOccupiedSpaces()
                >= room.getCapacity()) {

                throw new IllegalArgumentException(
                        "This room is already full"
                );
        }

        Booking booking = new Booking();

        booking.setStudent(student);
        booking.setRoom(room);
        booking.setBookingDate(LocalDate.now());

        room.setOccupiedSpaces(
                room.getOccupiedSpaces() + 1
        );

        roomRepository.save(room);

        return bookingRepository.save(booking);
    }
}