package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FailureScreen implements Screen {
	LudumDare34 game;
	Texture failureScreen;
	TextureRegion spaceBar;
	TextureRegion enter;
	TextureRegion continueTexture;
	TextureRegion mainMenuTexture;
	Sound loss;
	public FailureScreen(LudumDare34 game) {
		this.game = game;
		failureScreen = game.assets.get("BlobBurst.png");
		loss = game.assets.get("lose.wav");
		Texture ui = game.assets.get("UI.png");
		spaceBar = new TextureRegion(ui, 0, 0, 96, 32);
		enter = new TextureRegion(ui, 0, 32, 64, 32);
		continueTexture = new TextureRegion(ui, 0, 64, 128, 32);
		mainMenuTexture = new TextureRegion(ui, 0, 96, 128, 32);
	}
	@Override
	public void show() {
		loss.play();
	}
	float timer = 0;
	@Override
	public void render(float delta) {
		game.batch.begin();
		game.batch.draw(failureScreen, 0, 0, 800, 600);
		timer += delta;
		if (timer > 1) {
			game.batch.draw(spaceBar, 200, 100, 128, 64);
			game.batch.draw(continueTexture, 200, 36, 128, 64);
			game.batch.draw(enter, 500, 100, 128, 64);
			game.batch.draw(mainMenuTexture, 500, 36, 128, 64);
			if (Gdx.input.isKeyPressed(Keys.SPACE))
				game.start();
			else if (Gdx.input.isKeyJustPressed(Keys.ENTER))
				game.menu();
		}
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
