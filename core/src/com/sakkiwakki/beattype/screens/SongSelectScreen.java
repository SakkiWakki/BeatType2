package com.sakkiwakki.beattype.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sakkiwakki.beattype.BeatType;
import com.sakkiwakki.beattype.gameplay.Song;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SongSelectScreen implements Screen {
    private final BeatType game;

    private Stage stage;

    private Image selectImage;

    private ArrayList<Song> songs = new ArrayList<>();

    ////////////////
    // Constructor
    ////////////////

    public SongSelectScreen(BeatType game) {
        this.game = game;
        stage = new Stage(new FitViewport(BeatType.W_WIDTH, BeatType.W_HEIGHT, game.cam));


    }

    ////////////
    // Methods
    ////////////

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Texture selectTex = game.assets.get("Select/SongPanel.png", Texture.class);
        selectImage = new Image(selectTex);
        selectImage.setPosition(stage.getWidth() / 2 - 300, stage.getHeight() - 900);
        stage.addActor(selectImage);

        // I hate ImageTextButtons
        Texture buttonTex = game.assets.get("Select/SongSelectButton.png", Texture.class);
        ImageTextButton button1 = new ImageTextButton("Ah Hah Yeah",
                new ImageTextButton.ImageTextButtonStyle(new TextureRegionDrawable(new TextureRegion(buttonTex)),
                        new TextureRegionDrawable(new TextureRegion(buttonTex)),
                        new TextureRegionDrawable(new TextureRegion(buttonTex)),
                        game.font) );
        button1.setPosition(stage.getWidth() / 2 - 300 + 31, stage.getHeight() - 340);
        button1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(game.gameplayScreen);
            }
        });
        stage.addActor(button1);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.70f, 0.82f, 0.93f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
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
