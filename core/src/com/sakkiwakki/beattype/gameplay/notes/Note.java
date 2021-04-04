package com.sakkiwakki.beattype.gameplay.notes;

import com.badlogic.gdx.math.Rectangle;

public class Note {
    public int time;
    public Rectangle rectangle = new Rectangle();

    public Note(int time, int posX, int posY) {
        this.time = time;
        this.rectangle.x = time;
        this.rectangle.y = posY;
        this.rectangle.height = 64;
        this.rectangle.width = 64;
    }

}
