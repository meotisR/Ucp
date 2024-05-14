package com.ucp.controllers.templateControllers;

import com.ucp.service.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController extends Main {

    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

    @GetMapping("/error")
    public String error(Model model) {
        return "error-page";
    }
}
