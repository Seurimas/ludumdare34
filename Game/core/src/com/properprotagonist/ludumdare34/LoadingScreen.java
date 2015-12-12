package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LoadingScreen implements Screen {

	final LudumDare34 game;
	public LoadingScreen(LudumDare34 game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		float progress = game.assets.getProgress();
		game.shapes.begin(ShapeType.Line);
		game.shapes.setColor(Color.BLUE);
		game.shapes.rect(100, 200, 600, 100);
		game.shapes.end();
		game.shapes.begin(ShapeType.Filled);
		game.shapes.rect(100, 200, 600 * progress, 100);
		game.shapes.end();
		game.assets.update();
		if (game.assets.getProgress() >= 1)
			game.start();
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
