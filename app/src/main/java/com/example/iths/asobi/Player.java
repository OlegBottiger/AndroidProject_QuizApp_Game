package com.example.iths.asobi;

/**
 * Created by iths on 2015-11-25.
 */
public class Player {

    private final String name;
    private int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void increaseScore(int score) {
        this.score = this.score + score;
    }

}
