package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

    // user dashboard

    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public String UserDashboard() {

        return "/user/dashboard"; // in user folder there is a dashboard view.
    }
}
