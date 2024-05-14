package com.ucp.service;

import com.ucp.models.User;
import com.ucp.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    @Autowired
    protected UserRepository repoUsers;


    @Value("${upload.path}")
    protected String uploadPath;

    protected User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return repoUsers.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken) && auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            User user = repoUsers.findByUsername(userDetails.getUsername());
            if (user != null) {
                return user.getUsername();
            }
        }
        return null;
    }

    protected String getRole() {
        User users = getUser();
        if (users == null) return "NOT";
        return users.getRole().toString();
    }


    protected String getFio() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken) && auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            User user = repoUsers.findByUsername(userDetails.getUsername());
            if (user != null) {
                return user.getFio();
            }
        }
        return null;
    }

    protected String getStringDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = currentDate.format(formatter);

        return formattedDate;
    }

    protected String DateNow() {
        return LocalDateTime.now().toString().substring(0, 10);
    }
}
