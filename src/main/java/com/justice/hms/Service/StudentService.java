package com.justice.hms.Service;

import com.justice.hms.Model.Student;
import com.justice.hms.Repository.StudentRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Student createStudent(Student student) {

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException(
                    "A student with this email already exists");
        }

        if (studentRepository.existsByMatricNumber(student.getMatricNumber())) {
            throw new IllegalArgumentException(
                    "A student with this matric number already exists");
        }

        student.setPassword(
                passwordEncoder.encode(student.getPassword())
        );

        return studentRepository.save(student);
    }
}