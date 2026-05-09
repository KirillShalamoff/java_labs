package org.openjfx.Managers;

public class ScoreEntry {
    private int score;
    private String name;

    public ScoreEntry(String name ,int score) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
