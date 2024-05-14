package com.ucp.controllers;

import com.ucp.service.Main;
import com.ucp.models.User;
import com.ucp.models.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class LoginRegController extends Main {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("role", getRole());
        return "login";
    }

    @GetMapping("/reg")
    public String reg(Model model) {
        model.addAttribute("role", getRole());
        return "reg";
    }

    @PostMapping("/reg")
    public ResponseEntity<Object> addUser(Model model, @RequestBody Map<String, Object> user) {
        String fio = (String) user.get("fio");
        String username = (String) user.get("username");
        String password = (String) user.get("password");


        if (repoUsers.findAll().size() == 0 || repoUsers.findAll().isEmpty()) {
            repoUsers.save(new User(username, fio, password, getStringDate(), Role.ADMIN, "https://static-00.iconduck.com/assets.00/user-profile-icon-512x512-btn8fpvc.png"));
            return ResponseEntity.ok("Администратор зарегистрирован, можете авторизоваться");
        }

        User userFromDB = repoUsers.findByUsername(username);
        if (userFromDB != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь c таким логином существует");

        } else {
            repoUsers.save(new User(username, fio, password, getStringDate(), Role.USER, "https://static-00.iconduck.com/assets.00/user-profile-icon-512x512-btn8fpvc.png"));
        }

        return ResponseEntity.ok("Пользователь зарегистрирован, можете авторизоваться");
    }
}
