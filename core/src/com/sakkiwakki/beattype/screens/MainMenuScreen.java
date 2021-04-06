package com.sakkiwakki.beattype.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sakkiwakki.beattype.BeatType;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public final class MainMenuScreen implements Screen {

    private final BeatType game;

    private Stage stage;

    private Image logoImage;



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
        Gdx.input.setInputProcessor(stage);

        Texture logoTex = game.assets.get("MainMenu/Logo.png", Texture.class);
        logoImage = new Image(logoTex);
        stage.addActor(logoImage);
        logoImage.setPosition(stage.getWidth() / 2 - 401, stage.getHeight() / 2 - 270);
        logoImage.addAction(sequence(alpha(0f), fadeIn(2f)));

        //buttons here
        Texture exit = game.assets.get("MainMenu/ExitArrow.png", Texture.class);
        ImageButton exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exit)));
        exitButton.setPosition((stage.getWidth() / 2 - 401) - 113, (stage.getHeight() / 2 - 270) + 50);
        exitButton.addAction(sequence(alpha(0f), fadeIn(2f)));
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);

        Texture play = game.assets.get("MainMenu/PlayArrow.png", Texture.class);
        ImageButton playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(play)));
        playButton.setPosition((stage.getWidth() / 2 - 401) + 664, (stage.getHeight() / 2 - 270) + 50);
        playButton.addAction(sequence(alpha(0f), fadeIn(2f)));
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.songSelectScreen);
            }
        });
        stage.addActor(playButton);
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