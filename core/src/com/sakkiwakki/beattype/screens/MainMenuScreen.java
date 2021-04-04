package com.sakkiwakki.beattype.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sakkiwakki.beattype.BeatType;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MainMenuScreen implements Screen {

    private final BeatType game;

    private Stage stage;

    private Image logoImage;
    private Image playImage;
    private Image exitImage;

    ////////////////
    // Constructor
    ////////////////

    public MainMenuScreen(final BeatType game) {
        this.game = game;
        stage = new Stage(new FitViewport(BeatType.W_WIDTH, BeatType.W_HEIGHT, game.cam));

    }

    ////////////
    // Methods
    ////////////

    @Override
    public void show() {
        Texture logoTex = game.assets.get("MainMenu/Logo.png", Texture.class);
        logoImage = new Image(logoTex);
        stage.addActor(logoImage);

        Gdx.input.setInputProcessor(stage);
        logoImage.setPosition(stage.getWidth() / 2 - 401, stage.getHeight() / 2 - 270);
        logoImage.addAction(sequence(alpha(0f), fadeIn(2f)));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.70f, 0.82f, 0.93f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();

        //batch
        game.batch.begin();
        game.font.draw(game.batch, "bruh", 120, 120);
        game.batch.end();
    }

    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) { stage.getViewport().update(width, height, false); }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() {
        stage.dispose();
    }
}