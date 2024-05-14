package com.ucp.controllers.templateControllers;

import com.ucp.models.User;
import com.ucp.models.enums.Role;
import com.ucp.repo.UserRepository;
import com.ucp.service.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/moderate")
public class ModerationController extends Main {

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public String moderate(Model model) {
        if (getUser().getRole() == Role.ADMIN) {
            model.addAttribute("role", "DIRECTOR");
            return "moderation";
        } else if (getUser().getRole() == Role.ADMIN) {
            model.addAttribute("role", "ADMIN");
            return "moderation";
        } else {
            return "error-page";
        }
    }

    @GetMapping("/admins")
    public String moderateAdmins(Model model) {
        List<User> admins = userRepository.findAdminUsers();
        model.addAttribute("admins", admins);
        return "moderate-admins";
    }
}
