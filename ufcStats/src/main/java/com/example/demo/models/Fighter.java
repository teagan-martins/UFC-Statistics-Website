package com.example.demo.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="fighter")
public class Fighter {

    @Id
    @Column(name = "fighter_id")
    private int fighter_id;
    @Column
    private String first;
    @Column
    private String last;
    @Column
    private String nickname;
    @Column
    private String height;
    @Column
    private String weight;
    @Column
    private String reach;
    @Column
    private String stance;
    @Column
    private int wins;
    @Column
    private int losses;
    @Column
    private int draws;
    @Column
    private String belt;
    @OneToMany(targetEntity = Fight.class,cascade = CascadeType.ALL)
    @JoinColumn(name="fighter_id", referencedColumnName ="fighter_id")
    private List<Fight> fights;

    public Fighter(String first, String last, String nickname, String height, String weight, String reach, int wins, int losses, int draws, String belt) {
        this.first = first;
        this.last = last;
        this.nickname = nickname;
        this.height = height;
        this.weight = weight;
        this.reach = reach;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.belt = belt;
    }

    public Fighter() {

    }


    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getReach() {
        return reach;
    }

    public void setReach(String reach) {
        this.reach = reach;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public String getBelt() {
        return belt;
    }

    public void setBelt(String belt) {
        this.belt = belt;
    }

    public void setId(int fighter_id) {
        this.fighter_id = fighter_id;
    }

    public int getId() {
        return fighter_id;
    }

    public String getStance() {
        return stance;
    }

    public void setStance(String stance) {
        this.stance = stance;
    }
}
