package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name="fight")
public class Fight {

    @Column
    private String opponent;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fight_id;

    @ManyToOne(targetEntity = Fighter.class,cascade = CascadeType.ALL)
    @JoinColumn(name="fighter_id", referencedColumnName ="fighter_id")
    private Fighter fighter;

    public void setFight_id(int fightId) {
        this.fight_id = fightId;
    }

    public int getFight_id() {
        return fight_id;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }
}
