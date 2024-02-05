package com.example.demo.controllers;

import java.util.List;

import com.example.demo.models.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FightersController {

    @Autowired
    private FighterRepository fighterRepo;
    @Autowired
    private FightRepository fightRepo;

    @GetMapping("/showAll")
    public String getAllFighters(Model model) {
        // get all users from database
        List<Fighter> fighters = fighterRepo.findAll();
        // end of database call
        model.addAttribute("fighters", fighters);
        return "users/showAll";
    }

    @GetMapping("/search")
    public String searchFightersByName(@RequestParam(name = "name") String name, Model model, HttpSession session) {
        List<Fighter> fighters = fighterRepo.findByFirstStartingWithOrLastStartingWith(name, name);
        User user = (User) session.getAttribute("user");
        model.addAttribute("fighters", fighters);
        model.addAttribute("user",user);
        return "users/showAll";
    }

    @GetMapping("/fighterInfo")
    public String fighterDetails(@RequestParam(name = "fighterId") int fighterId, Model model, HttpSession session) {
        List<Fighter> fighters = fighterRepo.findById(fighterId);
        List<Fight> fights = fightRepo.findByFighter_id(fighterId);
        Fighter f = fighters.get(0);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("desiredFighter", f);
        model.addAttribute("fights",fights);
        return "users/showFighter";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        // get all users from database
        List<Fighter> fighters = fighterRepo.findAll();
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("fighters", fighters);
        return "users/showAll";
    }
}
