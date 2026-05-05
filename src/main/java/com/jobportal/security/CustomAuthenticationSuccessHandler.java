package com.jobportal.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        String redirectUrl = "/home"; // Default fallback

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String role = auth.getAuthority();
            
            if (role.equals("ROLE_ADMIN")) {
                redirectUrl = "/admin/dashboard";
                break;
            } else if (role.equals("ROLE_EMPLOYER")) {
                redirectUrl = "/employer/dashboard";
                break;
            } else if (role.equals("ROLE_STUDENT")) {
                redirectUrl = "/student/dashboard";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}
