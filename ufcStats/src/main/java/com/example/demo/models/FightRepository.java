package com.example.demo.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
public interface FightRepository extends JpaRepository<Fight,Integer>{
    List<Fight> findByFighter_id(int id);
}
