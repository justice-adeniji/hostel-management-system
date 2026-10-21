package com.justice.hms.Service;

import com.justice.hms.Model.Admin;
import com.justice.hms.Model.Student;
import com.justice.hms.Repository.AdminRepository;
import com.justice.hms.Repository.StudentRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;

    public CustomUserDetailsService(AdminRepository adminRepository,
                                    StudentRepository studentRepository) {
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Admin admin = adminRepository.findByEmail(email).orElse(null);

        if (admin != null) {
            return new User(
                    admin.getEmail(),
                    admin.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        Student student = studentRepository.findByEmail(email).orElse(null);

        if (student != null) {
            return new User(
                    student.getEmail(),
                    student.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_STUDENT"))
            );
        }

        throw new UsernameNotFoundException(
                "No account found with email: " + email
        );
    }
}