package com.ucp.controllers.templateControllers;

import com.ucp.models.User;
import com.ucp.repo.UserRepository;
import com.ucp.service.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class UserController extends Main {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    public String account(@PathVariable("id") String id, Model model) {
        Optional<User> userOptional = userRepository.findById(Long.valueOf(id));
        User user = userOptional.orElse(null);

        if (getUser() == user) {
            model.addAttribute("status", "owner");
        }

        model.addAttribute("user", user);

        return "user";
    }

    @GetMapping("/edit/user/{id}")
    public String editUser(@PathVariable("id") String id, Model model) {
        Optional<User> userOptional = userRepository.findById(Long.valueOf(id));
        User user = userOptional.orElse(null);

        model.addAttribute("user", user);

        return "edit-user";
    }


    @GetMapping("/edit/user-balance/{id}")
    public String editUserBalance(@PathVariable("id") String id, Model model) {
        Optional<User> userOptional = userRepository.findById(Long.valueOf(id));
        User user = userOptional.orElse(null);

        model.addAttribute("user", user);

        return "edit-balance";
    }

    @GetMapping("orders/user/{id}")
    public String orders(@PathVariable("id") String id, Model model) {
        Optional<User> userOptional = userRepository.findById(Long.valueOf(id));
        User user = userOptional.orElse(null);

        model.addAttribute("user", user);
        model.addAttribute("orders", user.getOrders());

        return "user-orders";
    }

    @GetMapping("/account")
    public String redirectToAccountPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/user/" + getUser().getId();
        } else {
            return "redirect:/login";
        }
    }
}
