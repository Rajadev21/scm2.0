package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // user dashboard
    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public String userDashboard() {

        return "/user/dashboard"; // in user folder there is a dashboard view.
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String userDashboardGet() {

        return "/user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public String userProfile() {
        System.out.println("user profile");
        return "/user/profile";
    }

}
