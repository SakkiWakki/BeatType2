package com.sakkiwakki.beattype.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.sakkiwakki.beattype.BeatType;

import static com.sakkiwakki.beattype.BeatType.W_HEIGHT;
import static com.sakkiwakki.beattype.BeatType.W_WIDTH;

public class DesktopLauncher {

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setResizable(false);
		config.setTitle("BeatType");
		config.setWindowedMode(W_WIDTH, W_HEIGHT);

		new Lwjgl3Application(new BeatType(), config);

	}
}
