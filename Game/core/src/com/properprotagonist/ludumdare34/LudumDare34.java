package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LudumDare34 extends Game {
	AssetManager assets = new AssetManager();
	public SpriteBatch batch;
	public ShapeRenderer shapes;
	@Override
	public void create () {
		assets.load("BlobBurst.png", Texture.class);
		assets.load("BlobFull.png", Texture.class);
		assets.load("Obstacles.png", Texture.class);
		assets.load("main_theme.wav", Music.class);
		assets.load("lose.wav", Sound.class);
		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
		loading();
	}
	public void loading() {
		setScreen(new LoadingScreen(this));
	}
	public void start() {
		setScreen(new MainScreen(this));
	}
	public void fail() {
		setScreen(new FailureScreen(this));
	}
}
