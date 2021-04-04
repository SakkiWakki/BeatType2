package com.sakkiwakki.beattype;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sakkiwakki.beattype.screens.LoadingScreen;
import com.sakkiwakki.beattype.screens.MainMenuScreen;
import com.sakkiwakki.beattype.screens.SongSelectScreen;
import com.sakkiwakki.beattype.screens.gameplay.TypeGameplay;

public class BeatType extends Game {
	public static int W_WIDTH = 1920;
	public static int W_HEIGHT = 1080;

	public OrthographicCamera cam;

	public SpriteBatch batch;
	public BitmapFont font;

	public AssetManager assets;

	public LoadingScreen loadingScreen;
	public MainMenuScreen mainMenuScreen;
	public SongSelectScreen songSelectScreen;
	public TypeGameplay typeGameplay;
	
	@Override
	public void create () {
		assets = new AssetManager();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, W_WIDTH, W_HEIGHT);

		loadingScreen = new LoadingScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		songSelectScreen = new SongSelectScreen(this);
		typeGameplay = new TypeGameplay(this);

		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(3,3);
		this.setScreen(loadingScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		assets.dispose();
		loadingScreen.dispose();
		mainMenuScreen.dispose();
	}
}
