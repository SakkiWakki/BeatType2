package com.sakkiwakki.beattype.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.sakkiwakki.beattype.BeatType;

public class ResultsScreen implements Screen {

    private TextureAtlas resultTexAtlas;
    private TextureAtlas judgeTexAtlas;
    private TextureAtlas numberTexAtlas;

    private final BeatType game;

    private String perfect, good, bad, miss, score;

    private Stage stage;

    public ResultsScreen(final BeatType game, String perfect, String good, String bad, String miss, String score) {
        this.game = game;
        this.perfect = perfect;
        this.good = good;
        this.bad = bad;
        this.miss = miss;
        this.score = score;
        resultTexAtlas = game.assets.get("Result/Result.atlas", TextureAtlas.class);
        judgeTexAtlas = game.assets.get("Gameplay/judgement/judgements.atlas", TextureAtlas.class);
        numberTexAtlas = game.assets.get("Gameplay/Number/Numbers.atlas", TextureAtlas.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.70f, 0.82f, 0.93f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        game.batch.begin();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        TextureRegion back = resultTexAtlas.findRegion("Back");
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(back)));
        backButton.setPosition(50, 50);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.songSelectScreen);
            }
        });
        stage.addActor(backButton);

        Image rankingPanel = new Image(resultTexAtlas.findRegion("RankingPanel"));
        rankingPanel.setPosition(game.cam.viewportWidth/2 - 700, game.cam.viewportHeight/4);
        stage.addActor(rankingPanel);

        Image et = new Image(resultTexAtlas.findRegion("ET"));
        et.setPosition(game.cam.viewportWidth/2 - 700 + 760, game.cam.viewportHeight/4 + 200);
        stage.addActor(et);

        Image perfect = new Image(judgeTexAtlas.findRegion("Perfect"));
        Image good = new Image(judgeTexAtlas.findRegion("Good"));
        Image bad = new Image(judgeTexAtlas.findRegion("Bad"));
        Image miss = new Image(judgeTexAtlas.findRegion("Miss"));

        perfect.setPosition(rankingPanel.getX()+20,rankingPanel.getY()+500);
        good.setPosition(rankingPanel.getX()+360,rankingPanel.getY()+500);
        bad.setPosition(rankingPanel.getX()+20,rankingPanel.getY()+300);
        miss.setPosition(rankingPanel.getX()+360,rankingPanel.getY()+300);

        stage.addActor(perfect);
        stage.addActor(good);
        stage.addActor(bad);
        stage.addActor(miss);

        renderNumber(this.perfect, (int)rankingPanel.getX()+20+80,(int)rankingPanel.getY()+500+10);
        renderNumber(this.good, (int)rankingPanel.getX()+360+80,(int)rankingPanel.getY()+500+10);
        renderNumber(this.bad, (int)rankingPanel.getX()+20+80,(int)rankingPanel.getY()+300+10);
        renderNumber(this.miss, (int)rankingPanel.getX()+360+80,(int)rankingPanel.getY()+300+10);
        renderNumber(score, 580, 340);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void renderNumber(String number, int x, int y) {
        int count = 0;
        for (char i : number.toCharArray()) {
            Image newNum = new Image(numberTexAtlas.findRegion(String.valueOf(i)));
            newNum.setPosition(x + 50 * (count + 1), y);
            stage.addActor(newNum);
            count++;
        }
    }
}
