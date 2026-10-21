package com.justice.hms.Controller;

import com.justice.hms.Model.Booking;
import com.justice.hms.Model.Student;
import com.justice.hms.Repository.BookingRepository;
import com.justice.hms.Repository.HostelRepository;
import com.justice.hms.Repository.RoomRepository;
import com.justice.hms.Repository.StudentRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

        private final HostelRepository hostelRepository;
        private final StudentRepository studentRepository;
        private final RoomRepository roomRepository;
        private final BookingRepository bookingRepository;

        public DashboardController(
                HostelRepository hostelRepository,
                StudentRepository studentRepository,
                RoomRepository roomRepository,
                BookingRepository bookingRepository) {

                this.hostelRepository = hostelRepository;
                this.studentRepository = studentRepository;
                this.roomRepository = roomRepository;
                this.bookingRepository = bookingRepository;
        }

        @GetMapping("/admin/dashboard")
        public String adminDashboard(Model model) {

                model.addAttribute(
                        "totalHostels",
                        hostelRepository.count()
                );

                model.addAttribute(
                        "totalStudents",
                        studentRepository.count()
                );

                model.addAttribute(
                        "totalRooms",
                        roomRepository.count()
                );

                int occupiedSpaces = roomRepository.findAll()
                        .stream()
                        .mapToInt(room -> room.getOccupiedSpaces())
                        .sum();

                model.addAttribute(
                        "occupiedSpaces",
                        occupiedSpaces
                );

                return "admin-dashboard";
        }

        @GetMapping("/student/dashboard")
        public String studentDashboard(
                Authentication authentication,
                Model model) {

                String email = authentication.getName();

                Student student = studentRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Student account not found"
                                ));

                Booking booking = bookingRepository
                        .findByStudentEmail(email) 
                        .orElse(null);

                model.addAttribute(
                        "student",
                        student
                );

        model.addAttribute(
                "booking",
                booking
        );

        return "student-dashboard";
    }
}