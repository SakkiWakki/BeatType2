package com.sakkiwakki.beattype.gameplay.notes;

public class Letter extends Note {
    private String letter;

    ////////////////
    // Constructors
    ////////////////

    public Letter(int time, int posX, int posY, String letter) {
        super(time, posX, posY);
        this.letter = letter;
    }

    public Letter(int time, int posX, int posY, int recWidth, int recHeight, String letter) {
        super(time, posX, posY);
        this.letter = letter;
        this.getRectangle().width = recWidth;
        this.getRectangle().height = recHeight;

    }

    /////////////
    // Methods
    /////////////

    public String getLetter() {
        return letter;
    }
}
