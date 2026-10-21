package com.justice.hms.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {

        return (request, response, authentication) -> {

            boolean isAdmin = authentication
                    .getAuthorities()
                    .stream()
                    .anyMatch(authority ->
                            authority.getAuthority()
                                    .equals("ROLE_ADMIN"));

            if (isAdmin) {

                response.sendRedirect(
                        "/admin/dashboard"
                );

            } else {

                response.sendRedirect(
                        "/student/dashboard"
                );
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/login")
                .permitAll()

                .requestMatchers("/css/**", "/js/**")
                .permitAll()

                .requestMatchers("/admin/**")
                .hasRole("ADMIN")

                .requestMatchers("/student/**")
                .hasRole("STUDENT")

                .requestMatchers("/hostels/**")
                .hasRole("ADMIN")

                .requestMatchers("/students/**")
                .hasRole("ADMIN")

                .requestMatchers("/rooms/**")
                .authenticated()

                .requestMatchers("/bookings/**")
                .hasRole("STUDENT")

                .anyRequest()
                .authenticated()
            )

            .formLogin(form -> form

                .loginPage("/login")

                .successHandler(
                        authenticationSuccessHandler()
                )

                .permitAll()
            )

            .logout(logout -> logout

                .logoutSuccessUrl(
                        "/login?logout"
                )

                .permitAll()
            );

        return http.build();
    }
}