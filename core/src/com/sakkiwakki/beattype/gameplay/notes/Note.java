package com.sakkiwakki.beattype.gameplay.notes;

import com.badlogic.gdx.math.Rectangle;

public class Note {
    private int time;
    private Rectangle rectangle = new Rectangle();
    private String color;

    ////////////////
    // Constructor
    ////////////////

    public Note(int time, int posX, int posY) {
        this.time = time;
        this.rectangle.x = posX;
        this.rectangle.y = posY;
        this.rectangle.height = 128;
        this.rectangle.width = 128;
    }

    public Note(String color, int time, float posX, float posY) {
        this.color = color;
        this.time = time;
        this.rectangle.x = posX;
        this.rectangle.y = posY;
        this.rectangle.height = 128;
        this.rectangle.width = 128;
    }

    /////////////
    // Methods
    /////////////

    public int getTime() {
        return time;
    }

    public String getColor() { return color; }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
