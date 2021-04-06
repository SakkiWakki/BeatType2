package com.sakkiwakki.beattype.screens.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.sakkiwakki.beattype.BeatType;
import com.sakkiwakki.beattype.gameplay.notes.Note;
import com.sakkiwakki.beattype.screens.ResultsScreen;

import java.text.DecimalFormat;

public final class SevenGameplay implements Screen, ChartParser, NoteBehavior, Controls, ChartBehavior {
    private final BeatType game;

    public OrthographicCamera cam;

    private Array<Array<Note>> lanes;
    private Array<Note> lane0;
    private Array<Note> lane1;
    private Array<Note> lane2;
    private Array<Note> lane3;
    private Array<Note> lane4;
    private Array<Note> lane5;
    private Array<Note> lane6;

    private TextureAtlas judgeTexAtlas;
    private TextureAtlas numberTexAtlas;
    private TextureAtlas noteTexAtlas;
    private Texture trackCircle;
    private Texture scoreTex;
    private Batch trackBatch;

    private ShapeRenderer shapeRenderer;

    private String judgementSprite;
    private int judgementX;
    private int judgementY;

    private Music song;

    //Chart attributes
    private int perfect;
    private int good;
    private int bad;
    private int miss;
    private int score;
    private float scrollSpeed;
    private double health;

    private double compareTo;
    private Timer timer;

    private Array<String> scoreNum;
    private DecimalFormat dfScore;

    ////////////////
    // Constructor
    ////////////////

    public SevenGameplay(BeatType game) {
        this.game = game;
        cam = new OrthographicCamera();
    }

    ////////////
    // Methods
    ////////////

    @Override
    public void show() {
        scrollSpeed = 3.15f; //IMPORTANT

        song = game.assets.get("Song/NO NIGHT MORE SOUL!.mp3");
        song.play();
        song.stop();

        dfScore = new DecimalFormat("0000000");
        scoreNum = new Array();
        for (int i = 0; i < 7; i++) {
            scoreNum.add("0");
        }

        lanes = new Array();
        lane0 = new Array();
        lane1 = new Array();
        lane2 = new Array();
        lane3 = new Array();
        lane4 = new Array();
        lane5 = new Array();
        lane6 = new Array();

        lanes.add(lane0);
        lanes.add(lane1);
        lanes.add(lane2);
        lanes.add(lane3);
        lanes.add(lane4);
        lanes.add(lane5);
        lanes.add(lane6);

        numberTexAtlas = game.assets.get("Gameplay/Number/Numbers.atlas", TextureAtlas.class);
        trackCircle = game.assets.get("Gameplay/Track/TrackCircle.png",Texture.class);
        scoreTex = game.assets.get("Gameplay/Track/Score.png",Texture.class);
        judgeTexAtlas = game.assets.get("Gameplay/judgement/judgements.atlas", TextureAtlas.class);
        noteTexAtlas = game.assets.get("Gameplay/Track/ManiaNotes.atlas", TextureAtlas.class);
        shapeRenderer = new ShapeRenderer();

        judgementSprite = "Blank";

        trackBatch = new SpriteBatch();

        timer = new Timer();

        cam.setToOrtho(false, game.cam.viewportWidth, game.cam.viewportHeight);

        parseChart("Song/NO NIGHT MORE SOUL!.txt", lanes);

        perfect = 0;
        good = 0;
        bad = 0;
        miss = 0;
        score = 0;
        health = 100;

        song.setOnCompletionListener(music ->
                game.setScreen(new ResultsScreen(game,
                        ""+perfect,
                        ""+good,
                        ""+bad,
                        ""+miss,
                        ""+score)));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        moveCamera(scrollSpeed);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //  HORRIBLE IMPLEMENTATION; DO NOT DO THIS IF YOU EVER USE THIS FRAMEWORK. I'M JUST TOO LAZY TO ACTUALLY  //
        //  MAKE A STAGE FOR THIS LOL                                                                              //
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(cam.viewportWidth/2 + 90 + 150 + 150 + 150 + 20, cam.viewportHeight/8 + 50, (float) 16, (float) health * 4);
        shapeRenderer.end();

        trackBatch.begin();
        trackBatch.draw(scoreTex, cam.viewportWidth + 200, cam.viewportHeight/2 - 30);
        trackBatch.draw(trackCircle, cam.viewportWidth/2 - 60 - 150 - 150 - 150, cam.viewportHeight/8, 140, 140);
        trackBatch.draw(trackCircle, cam.viewportWidth/2 - 60 - 150 - 150, cam.viewportHeight/8, 140, 140);
        trackBatch.draw(trackCircle, cam.viewportWidth/2 - 60 - 150, cam.viewportHeight/8, 140, 140);
        trackBatch.draw(trackCircle, cam.viewportWidth/2 - 60, cam.viewportHeight/8, 140, 140);
        trackBatch.draw(trackCircle, cam.viewportWidth/2 + 90, cam.viewportHeight/8, 140, 140);
        trackBatch.draw(trackCircle, cam.viewportWidth/2 + 90 + 150, cam.viewportHeight/8, 140, 140);
        trackBatch.draw(trackCircle, cam.viewportWidth/2 + 90 + 150 + 150, cam.viewportHeight/8, 140, 140);

        trackBatch.draw(judgeTexAtlas.findRegion(judgementSprite), judgementX, judgementY);
        trackBatch.end();

        game.batch.begin();
        noteSpawning();
        game.batch.end();

        update(delta);
    }

    public void update(float delta) {
        startGame();
        if (health > 100) health = 100;
        onDeath();
        lookForMiss();
    }

    public void lookForMiss() {
        for(Array<Note> lane: lanes)
            if (lane.size != 0)
                if (lane.get(0).getTime() + 130 <= song.getPosition() * 1000) {
                    lane.removeIndex(0);
                    showJudgement("Miss");
                    miss++;
                    health-=5;
                }
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

    //These methods go in update(float delta)

    @Override
    public Array<Note> parseChart(String file, Array lanes) {

        //Reads the file and splits each line onto an array
        FileHandle handle = Gdx.files.local(file);
        String text = handle.readString();
        String wordsArray[] = text.split("\\r?\\n");

        for (String str: wordsArray) {
            int i = Integer.parseInt(String.valueOf(str.charAt(0)));

            int time = Integer.parseInt(str.substring(str.indexOf(":") + 1));
            switch (i) {
                case 0:
                    lane0.add(new Note(
                            "white",
                            time,
                            cam.viewportWidth/2 - 60 - 150 - 150 - 150,
                            time * scrollSpeed));
                    break;
                case 1:
                    lane1.add(new Note(
                            "blue",
                            time,
                            cam.viewportWidth/2 - 60 - 150 - 150,
                            time * scrollSpeed));
                    break;
                case 2:
                    lane2.add(new Note(
                            "white",
                            time,
                            cam.viewportWidth/2 - 60 - 150,
                            time * scrollSpeed));
                    break;
                case 3:
                    lane3.add(new Note(
                            "orange",
                            time,
                            cam.viewportWidth/2 - 60,
                            time * scrollSpeed));
                    break;
                case 4:
                    lane4.add(new Note(
                            "white",
                            time,
                            cam.viewportWidth/2 + 90,
                            (float) (time * scrollSpeed)));
                    break;
                case 5:
                    lane5.add(new Note(
                            "blue",
                            time,
                            cam.viewportWidth/2 + 90 + 150,
                            (float) (time * scrollSpeed)));
                    break;
                case 6:
                    lane6.add(new Note(
                            "white",
                            time,
                            cam.viewportWidth/2 + 90 + 150 + 150,
                            (float) (time * scrollSpeed)));
                    break;
                default: System.out.println("Error in txt!");
            }
        }

        return lanes;

    }

    @Override
    public void noteSpawning() {
        for (Array<Note> arr: lanes)
            for (Note note: arr) {
                game.batch.draw(noteTexAtlas.findRegion(note.getColor()), note.getRectangle().x, note.getRectangle().y - 1080/3 , 140, 140);
            }


    }

    @Override
    public void controls() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override public boolean keyDown (int keycode) {
                compareTo = song.getPosition()*1000;
                onHit(keycode);
                return true;
            }
        });
    }

    private void onHit(int key) {
        switch(key) {

                case 45:
                    judgementBehaviorOnHit(0);
                    break;
                case 51:
                    judgementBehaviorOnHit(1);
                    break;
                case 33:
                    judgementBehaviorOnHit(2);
                    break;
                case 62:
                    judgementBehaviorOnHit(3);
                    break;
                case 111:
                    game.setScreen(game.songSelectScreen);
                    song.stop();
                    break;
                case 151:
                    judgementBehaviorOnHit(4);
                    break;
                case 152:
                    judgementBehaviorOnHit(5);
                    break;
                case 153:
                    judgementBehaviorOnHit(6);
                    break;
        }
    }

    private void judgementBehaviorOnHit(int lane) {
        if (lanes.get(lane).size >= 1)
            if (compareTo < lanes.get(lane).get(0).getTime() + 110
                    && compareTo > lanes.get(lane).get(0).getTime() - 110) {
                lanes.get(lane).removeIndex(
                        0);
                showJudgement("Perfect");
                perfect++;
                health+=2;
                score+=100;
                changeScore();
            }
            else if (compareTo < lanes.get(lane).get(0).getTime() + 120
                    && compareTo > lanes.get(lane).get(0).getTime() - 120) {
                lanes.get(lane).removeIndex(0);
                showJudgement("Good");
                good++;
                health+=1;
                score+=50;
                changeScore();
            }
            else if (compareTo < lanes.get(lane).get(0).getTime() + 130
                    && compareTo > lanes.get(lane).get(0).getTime() - 130) {
                lanes.get(lane).removeIndex(0);
                showJudgement("Bad");
                bad++;
                health += 1;
                score += 10;
                changeScore();
            }
    }

    @Override
    public void moveCamera(float speed) {
        cam.position.y = song.getPosition() * (speed * 1000);
    }

    @Override
    public void startGame() {
                song.play();
                controls();
    }

    @Override
    public void showJudgement(String judgement) {
        judgementSprite = judgement;
        judgementX = (int) cam.viewportWidth/2 - 75;
        judgementY = (int) cam.viewportHeight/2 + 100;
        judgementX += MathUtils.random(-10, 10);
        judgementY += MathUtils.random(-10, 10);

        timer.clear();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                judgementSprite = "Blank";
            }
        }, 2, 2, 0);
    }

    @Override
    public void changeScore() {
        String scoreString = dfScore.format(score);
        for (int i = 0; i < 7; i++) {
            scoreNum.set(6 - i, scoreString.substring(i, i + 1));
        }
    }

    @Override
    public void onDeath() {
        if (health <= 0) {
            song.stop();
            game.setScreen(new ResultsScreen(game,
                    ""+perfect,
                    ""+good,
                    ""+bad,
                    ""+miss,
                    ""+score));
        }
    }

    @Override
    public void dispose() {
        noteTexAtlas.dispose();
        trackCircle.dispose();
        trackBatch.dispose();
        scoreTex.dispose();
    }
}
