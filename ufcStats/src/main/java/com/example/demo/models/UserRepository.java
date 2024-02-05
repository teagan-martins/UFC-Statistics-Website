package com.example.demo.models;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByLoginAndPassword(String login, String password);

    Optional<User> findFirstByLogin(String login);

}