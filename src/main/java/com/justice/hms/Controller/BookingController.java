package com.justice.hms.Controller;

import com.justice.hms.Model.Booking;
import com.justice.hms.Repository.BookingRepository;
import com.justice.hms.Service.BookingService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bookings")
public class BookingController {

        private final BookingService bookingService;
        private final BookingRepository bookingRepository;

        public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
        }

        @PostMapping("/book")
        public String bookRoom(@RequestParam Integer roomId, Authentication authentication, RedirectAttributes redirectAttributes) {
                boolean isStudent = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_STUDENT"));

                if (!isStudent) {
                        redirectAttributes.addFlashAttribute(
                                "bookingError",
                                "Only students can book rooms."
                        );

                return "redirect:/rooms";
        }

        try {
                bookingService.bookRoom(
                authentication.getName(),
                roomId
        );

        redirectAttributes.addFlashAttribute(
                "bookingSuccess",
                "Room booked successfully!"
        );

        } catch (IllegalArgumentException e) {

                redirectAttributes.addFlashAttribute(
                        "bookingError",
                        e.getMessage()
                );
        }

        return "redirect:/rooms";
}

        @GetMapping("/my")
        public String myBooking(Authentication authentication, Model model) {
        Booking booking = bookingRepository
                .findByStudentEmail(authentication.getName())
                .orElse(null);

        model.addAttribute(
                "booking",
                booking
        );

        return "student-booking";
        }
}