package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups;

public class LoadingScreen implements Screen {

	final LudumDare34 game;
	private Texture splash = null;
	public LoadingScreen(LudumDare34 game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	float dbtbFlashProgress = 0;
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
		if (game.assets.getProgress() >= 1 && dbtbFlashProgress >= 1) {
			Sound bounce = game.assets.get("bounce.wav", Sound.class);
			Sound burst = game.assets.get("burst.wav", Sound.class);
			Sound pop = game.assets.get("pop.wav", Sound.class);
			Texture environment = game.assets.get("FloorAndCeiling.png", Texture.class);
			Texture obstacles = game.assets.get("Obstacles.png");
			Texture blobTexture = game.assets.get("BlobFull.png");
			Texture ui = game.assets.get("UI.png");
			Texture targets = game.assets.get("Targets.png");
			BitmapFont impact = game.assets.get("impact.fnt", BitmapFont.class);
			EngineSetup.setSounds(bounce, burst, pop);
			EngineSetup.setTextures(environment, obstacles, blobTexture, ui, targets);
			EngineSetup.setFonts(impact);
			Powerups.initialize(environment);
			game.menu();
		} else if (game.assets.getProgress() >= 1) {
			game.uiBatch.begin();
			if (splash == null)
				splash = game.assets.get("DontBurstTheBlob.png");
			game.uiBatch.draw(splash, 0, 0, 800, 600);
			dbtbFlashProgress += delta;
			game.uiBatch.end();
		}
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
