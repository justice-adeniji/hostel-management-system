package com.justice.hms.Config;

import com.justice.hms.Model.Admin;
import com.justice.hms.Repository.AdminRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner createDefaultAdmin(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (adminRepository.findByEmail("admin@hostel.com").isEmpty()) {

                Admin admin = new Admin();

                admin.setName("System Administrator");
                admin.setEmail("admin@hostel.com");
                admin.setPassword(
                        passwordEncoder.encode("admin123")
                );

                adminRepository.save(admin);

                System.out.println(
                        "Default admin account created: admin@hostel.com"
                );
            }
        };
    }
}