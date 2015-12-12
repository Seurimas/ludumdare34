package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class FailureScreen implements Screen {
	LudumDare34 game;
	Texture failureScreen;
	Sound loss;
	public FailureScreen(LudumDare34 game) {
		this.game = game;
		failureScreen = game.assets.get("BlobBurst.png");
		loss = game.assets.get("lose.wav");
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
			if (Gdx.input.isKeyPressed(Keys.SPACE))
				game.start();
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
