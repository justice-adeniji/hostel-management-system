package com.justice.hms.Controller;

import com.justice.hms.Model.Hostel;
import com.justice.hms.Service.HostelService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hostels")
public class HostelController {

    private final HostelService hostelService;

    public HostelController(HostelService hostelService) {
        this.hostelService = hostelService;
    }

    @GetMapping
    public String showHostels(Model model) {

        model.addAttribute(
                "hostels",
                hostelService.findAll()
        );

        return "hostels";
    }

    @GetMapping("/new")
    public String showCreateHostelForm(Model model) {

        model.addAttribute("hostel", new Hostel());

        return "hostel-form";
    }

    @PostMapping("/create")
    public String createHostel(
            @Valid @ModelAttribute("hostel") Hostel hostel,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "hostel-form";
        }

        hostelService.createHostel(hostel);

        return "redirect:/hostels";
    }

    @GetMapping("/edit/{id}")
    public String showEditHostelForm(
            @PathVariable Integer id,
            Model model) {

        Hostel hostel = hostelService.findById(id);

        model.addAttribute("hostel", hostel);

        return "hostel-form";
    }

    @PostMapping("/update/{id}")
    public String updateHostel(
            @PathVariable Integer id,
            @Valid @ModelAttribute("hostel") Hostel hostel,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "hostel-form";
        }

        try {

            hostelService.updateHostel(id, hostel);

        } catch (IllegalArgumentException e) {

            model.addAttribute("hostelError", e.getMessage());

            return "hostel-form";
        }

        return "redirect:/hostels";
    }

    @PostMapping("/delete/{id}")
    public String deleteHostel(
            @PathVariable Integer id,
            Model model) {

        try {

            hostelService.deleteHostel(id);

        } catch (IllegalArgumentException e) {

            model.addAttribute("hostelError", e.getMessage());

            model.addAttribute(
                    "hostels",
                    hostelService.findAll()
            );

            return "hostels";
        }

        return "redirect:/hostels";
    }
}