package com.example.demo.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FighterRepository extends JpaRepository<Fighter,Integer> {
    List<Fighter> findByFirstStartingWithOrLastStartingWith(String first, String last);

    List<Fighter> findById(int id);
}
