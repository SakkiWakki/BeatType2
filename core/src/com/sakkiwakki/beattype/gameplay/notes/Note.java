package com.sakkiwakki.beattype.gameplay.notes;

import com.badlogic.gdx.math.Rectangle;

public class Note {
    private int time;
    private Rectangle rectangle = new Rectangle();

    ////////////////
    // Constructor
    ////////////////

    public Note(int time, int posX, int posY) {
        this.time = time;
        this.rectangle.x = time;
        this.rectangle.y = posY;
        this.rectangle.height = 64;
        this.rectangle.width = 64;
    }

    /////////////
    // Methods
    /////////////

    public int getTime() {
        return time;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
