package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.UserService;
import com.scm.userform.Userform;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

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

    // @PostMapping("/authenticate")
    // public String processLogin() {
    // return "redirect:/user/dashboard";
    // }

    @RequestMapping("/signup")
    public String signup(Model model) { // here we are creating a empty object of userform. we have done this because we
                                        // can get the values of signup form.for that we have to do something in signup
                                        // page. (check it once).

        Userform userform = new Userform();
        model.addAttribute("userform", userform);
        return "Signup";
    }
    // handling signup form(the form will be submitte in /do-register)

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute Userform userform, BindingResult bindingResult,
            HttpSession session) {
        // step1 get the form data
        System.out.println(userform);

        // step2 validate form data

        if (bindingResult.hasErrors()) {
            return "Signup";
        }

        // step3 save to database
        // User user = User.builder()
        // .name(userform.getName())
        // .email(userform.getEmail())
        // .password(userform.getPassword())
        // .about(userform.getAbout())
        // .phoneNumber(userform.getPhoneNumber())
        // .profilePic(
        // "https://as2.ftcdn.net/v2/jpg/02/13/59/51/1000_F_213595138_QiDlxrtSWGBSj3q5JsjGohaNsF9vdtft.jpg")
        // .build();
        User user = new User();
        user.setName(userform.getName());
        user.setEmail(userform.getEmail());
        user.setPassword(userform.getPassword());
        user.setPhoneNumber(userform.getPhoneNumber());
        user.setAbout(userform.getAbout());
        user.setProfilePic(
                "https://as2.ftcdn.net/v2/jpg/02/13/59/51/1000_F_213595138_QiDlxrtSWGBSj3q5JsjGohaNsF9vdtft.jpg");

        User saveUser = userService.saveUser(user);

        // step4 success message
        Message alertMessage = Message.builder().content("Registration Successful").type(MessageType.blue).build();

        session.setAttribute("message", alertMessage);
        // step5 redirect to signup page
        return "redirect:/signup";
    }
}
