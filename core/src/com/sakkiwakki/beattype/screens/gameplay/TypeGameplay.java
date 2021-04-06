package com.sakkiwakki.beattype.screens.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.sakkiwakki.beattype.gameplay.notes.Letter;
import com.sakkiwakki.beattype.screens.ResultsScreen;

import java.text.DecimalFormat;
public class TypeGameplay implements Screen, ChartParser, NoteBehavior, Controls, ChartBehavior
     {
            private final BeatType game;

            public OrthographicCamera cam;

            private Array<Letter> letters;
            private TextureAtlas lettersTexAtlas;
            private TextureAtlas judgeTexAtlas;
            private TextureAtlas numberTexAtlas;
            private Texture track;
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
            private double health;

            private boolean gameNotStarted;
            private double compareTo;
            private Timer timer;

            private Array<String> scoreNum;
            private DecimalFormat dfScore;

            ////////////////
            // Constructor
            ////////////////

            public TypeGameplay(BeatType game) {
                this.game = game;
                cam = new OrthographicCamera();
            }

            ////////////
            // Methods
            ////////////

            @Override
            public void show() {
                song = game.assets.get("Song/Ah Hah Yeah.mp3");
                song.play();
                song.stop();

                dfScore = new DecimalFormat("000000");
                scoreNum = new Array();
                for (int i = 0; i < 6; i++) {
                    scoreNum.add("0");
                }

                letters = new Array();

                numberTexAtlas = game.assets.get("Gameplay/Number/Numbers.atlas", TextureAtlas.class);
                lettersTexAtlas = game.assets.get("Gameplay/letters/letters.atlas", TextureAtlas.class);
                track = game.assets.get("Gameplay/Track/Track.png",Texture.class);
                trackCircle = game.assets.get("Gameplay/Track/TrackCircle.png",Texture.class);
                scoreTex = game.assets.get("Gameplay/Track/Score.png",Texture.class);
                judgeTexAtlas = game.assets.get("Gameplay/judgement/judgements.atlas", TextureAtlas.class);
                shapeRenderer = new ShapeRenderer();

                judgementSprite = "Blank";

                trackBatch = new SpriteBatch();

                timer = new Timer();

                cam.setToOrtho(false, game.cam.viewportWidth, game.cam.viewportHeight);

                parseChart("Song/Ah Hah Yeah.txt", letters);

                perfect = 0;
                good = 0;
                bad = 0;
                miss = 0;
                score = 0;
                health = 100;

                gameNotStarted = true;

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
                Gdx.gl.glClearColor(0.70f, 0.82f, 0.93f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                cam.update();
                game.batch.setProjectionMatrix(cam.combined);

                moveCamera(1000);

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //  HORRIBLE IMPLEMENTATION; DO NOT DO THIS IF YOU EVER USE THIS FRAMEWORK. I'M JUST TOO LAZY TO ACTUALLY  //
                //  MAKE A STAGE FOR THIS LOL                                                                              //
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////

                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.rect(160, game.cam.viewportHeight / 2 - 8, (float) health * 4, 16);
                shapeRenderer.end();

                trackBatch.begin();
                trackBatch.draw(track, 0, 700);
                trackBatch.draw(scoreTex, 1100, cam.viewportHeight/2 - 30);
                trackBatch.draw(trackCircle, cam.viewportWidth/4 - 80, 800 - 80);
                trackBatch.draw(judgeTexAtlas.findRegion(judgementSprite), judgementX, judgementY);
                trackBatch.draw(numberTexAtlas.findRegion(scoreNum.get(5)), 1125, cam.viewportHeight/2);
                trackBatch.draw(numberTexAtlas.findRegion(scoreNum.get(4)), 1195, cam.viewportHeight/2);
                trackBatch.draw(numberTexAtlas.findRegion(scoreNum.get(3)), 1265, cam.viewportHeight/2);
                trackBatch.draw(numberTexAtlas.findRegion(scoreNum.get(2)), 1335, cam.viewportHeight/2);
                trackBatch.draw(numberTexAtlas.findRegion(scoreNum.get(1)), 1405, cam.viewportHeight/2);
                trackBatch.draw(numberTexAtlas.findRegion(scoreNum.get(0)), 1475, cam.viewportHeight/2);
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
                if (letters.size != 0)
                    if (letters.get(0).getTime() + 100 <= song.getPosition() * 1000) {
                        letters.removeIndex(0);
                        showJudgement("Miss");
                        System.out.println("Miss");
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
            public Array<Letter> parseChart(String file, Array letters) {

                //Reads the file and splits each line onto an array
                FileHandle handle = Gdx.files.local(file);
                String text = handle.readString();
                String wordsArray[] = text.split("\\r?\\n");

                //Here is where the notes are created
                for (String line: wordsArray) {
                    letters.add(new Letter(Integer.parseInt(line.substring(2)) + 0,
                                           Integer.parseInt(line.substring(2)) + 1980,
                                           768,
                                           64,
                                           64,
                                           line.substring(0,1).equals(" ") ? "SPACE": line.substring(0,1)));
                }
                return letters;
            }

            @Override
            public void noteSpawning() {
                for (Letter l: letters) {
                    game.batch.draw(lettersTexAtlas.findRegion(l.getLetter()), l.getTime() + 1980, l.getRectangle().y);
                }
            }

            @Override
            public void controls() {
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override public boolean keyDown (int keycode) {
                        if (letters.size != 0) {
                            if (keycode == Input.Keys.valueOf(letters.get(0).getLetter())
                                    || (keycode == Input.Keys.SPACE && letters.get(0).getLetter() == "SPACE")) {

                                compareTo = song.getPosition()*1000;

                                if (compareTo < letters.get(0).getTime() + 50
                                        && compareTo > letters.get(0).getTime() - 50) {
                                    letters.removeIndex(0);
                                    showJudgement("Perfect");
                                    System.out.println("Perfect");
                                    perfect++;
                                    health+=2;
                                    score+=100;
                                    changeScore();
                                }
                                else if (compareTo < letters.get(0).getTime() + 75
                                        && compareTo > letters.get(0).getTime() - 75) {
                                    letters.removeIndex(0);
                                    showJudgement("Good");
                                    System.out.println("Good");
                                    good++;
                                    health+=1;
                                    score+=50;
                                    changeScore();
                                }
                                else if (compareTo < letters.get(0).getTime() + 100
                                        && compareTo > letters.get(0).getTime() - 100) {
                                    letters.removeIndex(0);
                                    showJudgement("Bad");
                                    System.out.println("Bad");
                                    bad++;
                                    health+=1;
                                    score+=10;
                                    changeScore();
                                }
                            }
                            if (keycode == 111) {
                                game.setScreen(game.songSelectScreen);
                                song.stop();
                            }
                        }
                        return true;
                    }
                });
            }

            @Override
            public void moveCamera(float speed) {
                cam.position.x += speed * Gdx.graphics.getDeltaTime();
            }

            @Override
            public void startGame() {
                if (gameNotStarted)
                    if (cam.position.x >= 1980 * 1.25) {
                        gameNotStarted = false;
                        song.play();
                        controls();
                    }
            }

            @Override
            public void showJudgement(String judgement) {
                judgementSprite = judgement;
                judgementX = (int) cam.viewportWidth/4 - 80;
                judgementY = 800 - 180;
                judgementX += MathUtils.random(-10, 10);
                judgementY += MathUtils.random(-10, 10);
                if (judgementY <= 570)
                    judgementY = 570;
                if (judgementX < 0)
                    judgementX = 0;
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
                for (int i = 0; i < 6; i++) {
                    scoreNum.set(5 - i, scoreString.substring(i, i + 1));
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
                lettersTexAtlas.dispose();
                track.dispose();
                trackBatch.dispose();
                trackCircle.dispose();
                scoreTex.dispose();
            }
}
