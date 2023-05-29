package model;

import Controller.Court;

public class Player {
    private String nom;
    private int id;
    private int score;
    private int bonus;
    private Court c;

    public Player(int id, String nom, Court c) {
        this.id = id;
        this.nom = nom;
        this.c = c;
    }

    public Player() {
        this.nom = "Player";
    }

    public void setName(String nom) {
        this.nom = nom;
    }

    public void setScore(int score) {
        this.score = score;

    }

    public void incBonus(int bonus) {
        this.bonus += bonus;
        c.updateBonus(bonus);
    }

    public void incScore(int incm) {
        this.score += incm;
        c.updateBonus(this.score);
    }

    public void resetScore() {
        this.score = 0;
    }

    public void resetBonus() {
        this.bonus = 0;
    }

    public String getNom() {
        return this.nom;
    }

    public int getId() {
        return this.id;
    }

    public int getScore() {
        return this.score;
    }

    public int getbonus() {
        return this.bonus;
    }

}