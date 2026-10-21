package com.justice.hms.Config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

        @ModelAttribute("isAdmin")
        public boolean isAdmin() {Authentication authentication =
                        SecurityContextHolder.getContext().getAuthentication();

                return authentication != null
                        && authentication.getAuthorities().stream()
                        .anyMatch(authority ->
                                authority.getAuthority().equals("ROLE_ADMIN"));
        }

        @ModelAttribute("isStudent")
        public boolean isStudent() {

                Authentication authentication =
                        SecurityContextHolder.getContext().getAuthentication();

                return authentication != null
                        && authentication.getAuthorities().stream()
                        .anyMatch(authority ->
                                authority.getAuthority().equals("ROLE_STUDENT"));
        }
}