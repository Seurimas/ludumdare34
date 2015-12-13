package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.debug.DebugEntityRenderer;
import com.properprotagonist.ludumdare34.debug.DebugWeightRenderer;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.blob.BlobBits;
import com.properprotagonist.ludumdare34.ecs.blob.BlobComponent;
import com.properprotagonist.ludumdare34.ecs.blob.BlobGrowthController;
import com.properprotagonist.ludumdare34.ecs.blob.BlobRenderer;
import com.properprotagonist.ludumdare34.ecs.blob.Burst;
import com.properprotagonist.ludumdare34.ecs.blob.BurstSystem;
import com.properprotagonist.ludumdare34.ecs.blob.BlobRenderer.BlobSprite;
import com.properprotagonist.ludumdare34.ecs.bounce.BounceMessage;
import com.properprotagonist.ludumdare34.ecs.bounce.BouncinessComponent;
import com.properprotagonist.ludumdare34.ecs.bounce.BouncinessSystem;
import com.properprotagonist.ludumdare34.ecs.bounce.FloorSystem;
import com.properprotagonist.ludumdare34.ecs.danger.DangerousObstacle;
import com.properprotagonist.ludumdare34.ecs.danger.PoppedBlobListener;
import com.properprotagonist.ludumdare34.ecs.danger.RailObstacleSpawner;
import com.properprotagonist.ludumdare34.ecs.gravity.FallingSpeed;
import com.properprotagonist.ludumdare34.ecs.gravity.GravitySystem;
import com.properprotagonist.ludumdare34.ecs.gravity.GrowthDetector;
import com.properprotagonist.ludumdare34.ecs.gravity.GrowthSystem;
import com.properprotagonist.ludumdare34.ecs.gravity.Weight;
import com.properprotagonist.ludumdare34.ecs.gravity.WeightGrowth;
import com.properprotagonist.ludumdare34.ecs.powerups.PowerupRenderer;
import com.properprotagonist.ludumdare34.ecs.powerups.PowerupSystem;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups;
import com.properprotagonist.ludumdare34.ecs.powerups.RailPowerupSpawner;
import com.properprotagonist.ludumdare34.ecs.rail.Collider;
import com.properprotagonist.ludumdare34.ecs.rail.Obstacle;
import com.properprotagonist.ludumdare34.ecs.rail.RailTargetCamera;
import com.properprotagonist.ludumdare34.ecs.rail.RailVelocity;
import com.properprotagonist.ludumdare34.ecs.rail.systems.CollisionMessage;
import com.properprotagonist.ludumdare34.ecs.rail.systems.CollisionSystem;
import com.properprotagonist.ludumdare34.ecs.rail.systems.RailMovementSystem;
import com.properprotagonist.ludumdare34.ecs.rail.systems.RailScopeSystem;
import com.properprotagonist.ludumdare34.ecs.render.BackdropRenderer;
import com.properprotagonist.ludumdare34.ecs.render.DistanceRenderer;
import com.properprotagonist.ludumdare34.ecs.render.Extended9PatchRenderer;
import com.properprotagonist.ludumdare34.ecs.render.Extended9PatchRenderer.Extended9Patch;
import com.properprotagonist.ludumdare34.ecs.render.SimpleSpriteRenderer;
import com.properprotagonist.ludumdare34.ecs.render.SimpleSpriteRenderer.SimpleSprite;
import com.properprotagonist.ludumdare34.ecs.toast.QuoteSystem;

public class MainScreen implements Screen {
	LudumDare34 game;
	Engine engine;
	Entity player;
	Music theme;
	OrthographicCamera camera;
	Matrix4 stored = new Matrix4();
	private final float deltaMod;
	public MainScreen(LudumDare34 game, float deltaMod, boolean powerups) {
		this.game = game;
		engine = new Engine();
		camera = new OrthographicCamera(800, 600);
		theme = game.assets.get("main_theme.mp3");
		EngineSetup.addCriticalSystems(engine);
		player = EngineSetup.createPlayer(engine, true);
		EngineSetup.setupBasicGame(engine, player, powerups);
		
		EngineSetup.setupRendering(engine, player, camera, game.uiBatch, true);
		this.deltaMod = deltaMod;
	}
	public Engine getEngine() {
		return engine;
	}
	@Override
	public void show() {
		stored.set(game.batch.getProjectionMatrix());
		theme.setLooping(true);
		theme.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		engine.act(delta * deltaMod);
		game.batch.begin();
		engine.draw(game.batch, game.shapes);
		game.batch.end();
		if (engine.failed())
			game.fail((int)(player.getBounding().x / 200));
	}

	@Override
	public void resize(int width, int height) {
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
		theme.stop();
		game.batch.setProjectionMatrix(stored);
		game.shapes.setProjectionMatrix(stored);
	}

	@Override
	public void dispose() {
		theme.stop();
	}

}
