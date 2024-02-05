package com.example.demo.service;

import com.example.demo.models.Fighter;
import com.example.demo.models.FighterRepository;
import com.example.demo.models.User;
import com.example.demo.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FighterRepository fighterRepo;

    public UserService (UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User registerUser(String login, String password, String email, String favouriteFighter) {
        if (login==null || password ==null) {
            return null;
        }
        else {
            if (userRepo.findFirstByLogin(login).isPresent()) {
                System.out.println("Duplicate login");
                return null;
            }
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            user.setFavouriteFighter(favouriteFighter);
            return userRepo.save(user);
        }
    }

    public User authenticate(String login, String password) {
        return userRepo.findByLoginAndPassword(login,password).orElse(null);
    }

    public User updateFavouriteFighter(int userId, int fighterId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Fighter> fighters = fighterRepo.findById(fighterId);
            user.setFavouriteFighter(fighters.get(0).getFirst() + " " + fighters.get(0).getLast()); // duplicate names possible - fix

            userRepo.save(user);
            return user;
        }
        return null;
    }
}
