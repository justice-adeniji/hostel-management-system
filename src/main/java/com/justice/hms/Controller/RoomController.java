package com.justice.hms.Controller;

import com.justice.hms.Model.Hostel;
import com.justice.hms.Repository.HostelRepository;
import com.justice.hms.Repository.RoomRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepository roomRepository;
    private final HostelRepository hostelRepository;

    public RoomController(RoomRepository roomRepository, HostelRepository hostelRepository) {
        this.roomRepository = roomRepository;
        this.hostelRepository = hostelRepository;
    }

    @GetMapping
    public String showRooms(Model model) {

        List<Hostel> hostels = hostelRepository.findAll();

        model.addAttribute("hostels", hostels);
        model.addAttribute("rooms", roomRepository.findAll());

        return "rooms";
    }

    @GetMapping("/available")
    public String showAvailableRooms(Model model) {

        List<Hostel> hostels = hostelRepository.findAll();

        model.addAttribute("hostels", hostels);
        model.addAttribute(
                "rooms",
                roomRepository.findAvailableRooms()
        );

        return "rooms";
    }
}