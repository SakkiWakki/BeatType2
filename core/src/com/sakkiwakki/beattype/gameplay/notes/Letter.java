package com.sakkiwakki.beattype.gameplay.notes;

public class Letter extends Note {
    private String letter;

    ////////////////
    // Constructor
    ////////////////

    public Letter(int time, int posX, int posY, String letter) {
        super(time, posX, posY);
        this.letter = letter;
    }

    /////////////
    // Methods
    /////////////

    public String getLetter() {
        return letter;
    }
}
