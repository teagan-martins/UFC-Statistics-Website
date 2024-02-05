package com.example.demo.controllers;

import com.example.demo.models.Fighter;
import com.example.demo.models.FighterRepository;
import com.example.demo.models.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FighterRepository fighterRepo;

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new User());
        return "users/register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new User());
        return"users/login_page";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        String favouriteFighter = user.getFavouriteFighter();
        model.addAttribute("favouriteFighter",session.getAttribute("favouriteFighter"));
        return "users/profile_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        System.out.println("register request: " + user);
        User registeredUser = userService.registerUser(user.getLogin(),user.getPassword(),user.getEmail(),"None");
        return registeredUser==null ? "users/error_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpSession session) {
        System.out.println("login request: " + user);
        User authenticated = userService.authenticate(user.getLogin(), user.getPassword());
        List<Fighter> fighters = fighterRepo.findAll();
        if (authenticated != null) {
            session.setAttribute("user", authenticated);
            model.addAttribute("user", authenticated);
            model.addAttribute("fighters", fighters);
            return "users/showAll";
        } else {
            return "users/error_page";
        }
    }

    @PostMapping("/makeFavorite/{fighterId}")
    public String makeFavorite(@PathVariable int fighterId, HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            System.out.println(fighterId);
            userService.updateFavouriteFighter(user.getId(), fighterId);
            List fighters = fighterRepo.findById(fighterId);
            model.addAttribute("user",user);
            model.addAttribute("favouriteFighter", fighters.get(0));
            session.setAttribute("favouriteFighter", fighters.get(0));
            return "users/updated_profile_page";
        } else {
            // Handle the case where the user is not logged in
            return "redirect:/login";
        }
    }



}
