package com.justice.hms.Controller;

import com.justice.hms.Model.Student;
import com.justice.hms.Repository.StudentRepository;
import com.justice.hms.Service.StudentService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService,
                             StudentRepository studentRepository) {

        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String showStudents(Model model) {

        model.addAttribute(
                "students",
                studentRepository.findAll()
        );

        return "students";
    }

    @GetMapping("/new")
    public String showCreateStudentForm(Model model) {

        model.addAttribute(
                "student",
                new Student()
        );

        return "student-form";
    }

    @PostMapping("/create")
    public String createStudent(
            @Valid @ModelAttribute("student") Student student,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "student-form";
        }

        try {

            studentService.createStudent(student);

        } catch (IllegalArgumentException e) {

            bindingResult.reject(
                    "studentError",
                    e.getMessage()
            );

            return "student-form";
        }

        return "redirect:/students";
    }
}