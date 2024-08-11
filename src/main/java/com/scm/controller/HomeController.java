package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "Raja Bomma");
        return "Home";
    }

    @RequestMapping("/about")
    public String about() {

        return "About";
    }

    @RequestMapping("/services")
    public String services() {
        return "Services";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "Contact";
    }

    @RequestMapping("/login")
    public String login() {

        return "Login";
    }

    @RequestMapping("/signup")
    public String signup() {

        return "Signup";
    }
}
