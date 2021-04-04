package com.sakkiwakki.beattype.screens.gameplay;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.sakkiwakki.beattype.BeatType;
import com.sakkiwakki.beattype.gameplay.notes.Letter;
import com.sakkiwakki.beattype.gameplay.notes.Note;

public class TypeGameplay implements Screen, ChartParser, NoteBehavior, Controls {
    private final BeatType game;

    OrthographicCamera cam = new OrthographicCamera();

    Array<Letter> letters;

    ////////////////
    // Constructor
    ////////////////

    public TypeGameplay(BeatType game) {
        this.game = game;

    }

    ////////////
    // Methods
    ////////////

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        update(delta);
    }

    public void update(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    //These methods goes in update(float delta)

    @Override
    public void parseChart(String file, Array<Note> notes) {

    }

    @Override
    public void noteSpawning(Array<Note> notes) {

    }

    @Override
    public void onHit() {

    }

    @Override
    public void hitJudgement() {

    }

    @Override
    public void controls() {

    }
}
