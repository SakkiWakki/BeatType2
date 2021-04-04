package com.sakkiwakki.beattype.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.sakkiwakki.beattype.BeatType;

public class LoadingScreen implements Screen {

    private final BeatType game;

    private ShapeRenderer shapeRenderer;

    private float loadProgress;

    ////////////////
    // Constructor
    ////////////////

    public LoadingScreen(final BeatType game) {
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
    }

    ////////////
    // Methods
    ////////////

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.70f, 0.82f, 0.93f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        game.batch.begin();
        game.font.draw(game.batch, "LOADING", 20, 50);
        game.batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(160, game.cam.viewportHeight / 2 - 8, game.cam.viewportWidth - 320, 16);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(160, game.cam.viewportHeight / 2 - 8, loadProgress * (game.cam.viewportWidth - 320), 16);
        shapeRenderer.end();
    }

    public void update(float delta) {
        loadProgress = MathUtils.lerp(loadProgress, game.assets.getProgress(), 0.1f);

        if (game.assets.update() && loadProgress >= game.assets.getProgress() - 0.001f) {
            game.setScreen(game.mainMenuScreen);
        }
    }

    @Override
    public void show() {
        this.loadProgress = 0f;
        queueAssets();
    }
    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    private void queueAssets() {
        game.assets.load("MainMenu/Logo.png", Texture.class);
    }
}
