package com.sakkiwakki.beattype.gameplay.notes;

public class Letter extends Note {
    public String letter;

    public Letter(int time, int posX, int posY, String letter) {
        super(time, posX, posY);
        this.letter = letter;
    }
}
