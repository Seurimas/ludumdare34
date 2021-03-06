package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.properprotagonist.ludumdare34.ecs.menu.NextModeSystem.Mode;

public class LudumDare34 extends Game {
	public static final float SCREEN_HEIGHT = 600;
	public static final float SCREEN_WIDTH = 800;
	AssetManager assets = new AssetManager();
	public SpriteBatch batch;
	public SpriteBatch uiBatch;
	public ShapeRenderer shapes;
	@Override
	public void create () {
		assets.load("DontBurstTheBlob.png", Texture.class);
		assets.load("BlobBurst.png", Texture.class);
		assets.load("BlobFull.png", Texture.class);
		assets.load("FloorAndCeiling.png", Texture.class);
		assets.load("Background.png", Texture.class);
		assets.load("Obstacles.png", Texture.class);
		assets.load("Targets.png", Texture.class);
		assets.load("UI.png", Texture.class);
		assets.load("main_theme.mp3", Music.class);
		assets.load("bounce.wav", Sound.class);
		assets.load("burst.wav", Sound.class);
		assets.load("pop.wav", Sound.class);
		assets.load("lose.wav", Sound.class);
		assets.load("impact.fnt", BitmapFont.class);
		batch = new SpriteBatch();
		uiBatch = new SpriteBatch();
		shapes = new ShapeRenderer();
		loading();
	}
	public void loading() {
		setScreen(new LoadingScreen(this));
	}
	public Mode mode;
	public void start() {
		mode.begin(this);
	}
	public void start(float deltaMod, boolean powerups, Mode mode) {
		this.mode = mode;
		setScreen(new MainScreen(this, deltaMod, powerups));
	}
	public void menu() {
		setScreen(new MenuScreen(this));
	}
	public void fail(int distance) {
		mode.setHighScore(distance);
		setScreen(new FailureScreen(this));
	}
}
