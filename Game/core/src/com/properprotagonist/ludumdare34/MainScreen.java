package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.debug.DebugEntityRenderer;
import com.properprotagonist.ludumdare34.debug.DebugRailObstacleRenderer;
import com.properprotagonist.ludumdare34.debug.DebugRailRenderer;
import com.properprotagonist.ludumdare34.debug.DebugWeightRenderer;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.bounce.BouncinessComponent;
import com.properprotagonist.ludumdare34.ecs.bounce.BouncinessSystem;
import com.properprotagonist.ludumdare34.ecs.bounce.FloorSystem;
import com.properprotagonist.ludumdare34.ecs.danger.DangerousObstacle;
import com.properprotagonist.ludumdare34.ecs.danger.PoppedBlobListener;
import com.properprotagonist.ludumdare34.ecs.gravity.FallingSpeed;
import com.properprotagonist.ludumdare34.ecs.gravity.GravitySystem;
import com.properprotagonist.ludumdare34.ecs.gravity.GrowthDetector;
import com.properprotagonist.ludumdare34.ecs.gravity.GrowthSystem;
import com.properprotagonist.ludumdare34.ecs.gravity.Weight;
import com.properprotagonist.ludumdare34.ecs.gravity.WeightGrowth;
import com.properprotagonist.ludumdare34.ecs.rail.RailObstacle;
import com.properprotagonist.ludumdare34.ecs.rail.RailPosition;
import com.properprotagonist.ludumdare34.ecs.rail.RailTargetCamera;
import com.properprotagonist.ludumdare34.ecs.rail.RailVelocity;
import com.properprotagonist.ludumdare34.ecs.rail.systems.CollisionMessage;
import com.properprotagonist.ludumdare34.ecs.rail.systems.RailCollisionSystem;
import com.properprotagonist.ludumdare34.ecs.rail.systems.RailMovementSystem;
import com.properprotagonist.ludumdare34.ecs.rail.systems.RailPositioningSystem;
import com.properprotagonist.ludumdare34.ecs.rail.systems.RailScopeSystem;
import com.properprotagonist.ludumdare34.ecs.rail.systems.SingleRailObstacleSystem;
import com.properprotagonist.ludumdare34.ecs.render.BlobRenderer;
import com.properprotagonist.ludumdare34.ecs.render.BlobRenderer.BlobSprite;
import com.properprotagonist.ludumdare34.ecs.render.Extended9PatchRenderer;
import com.properprotagonist.ludumdare34.ecs.render.Extended9PatchRenderer.Extended9Patch;
import com.properprotagonist.ludumdare34.ecs.render.SimpleSpriteRenderer;
import com.properprotagonist.ludumdare34.ecs.render.SimpleSpriteRenderer.SimpleSprite;

public class MainScreen implements Screen {
	LudumDare34 game;
	Engine engine;
	Entity dummy;
	Music theme;
	OrthographicCamera camera;
	Matrix4 stored = new Matrix4();
	public MainScreen(LudumDare34 game) {
		this.game = game;
		engine = new Engine();
		camera = new OrthographicCamera(800, 600);
		theme = game.assets.get("main_theme.wav");
		createPlayer();
		createObstacle();
		engine.addRenderer(new RailTargetCamera(dummy, camera));
		// RAILS
		engine.addComponentSystem(new RailMovementSystem());
		engine.addComponentSystem(new RailPositioningSystem());
		engine.addComponentSystem(new RailCollisionSystem(dummy));
		engine.addComponentSystem(new SingleRailObstacleSystem());
		engine.addComponentSystem(new RailScopeSystem(dummy));
		// GRAVITY
		engine.addComponentSystem(new GravitySystem(5, -15));
		engine.addComponentSystem(new GrowthSystem());
		engine.addComponentSystem(new FloorSystem());
		engine.addComponentSystem(new BouncinessSystem(6));
		// DANGER
		engine.addListener(CollisionMessage.class, new PoppedBlobListener());
		
		engine.addRenderer(new DebugWeightRenderer());
		engine.addRenderer(new DebugEntityRenderer());
		engine.addRenderer(new DebugRailRenderer());
		engine.addRenderer(new DebugRailObstacleRenderer());
		engine.addRenderer(new SimpleSpriteRenderer());
		engine.addRenderer(new Extended9PatchRenderer());
		engine.addRenderer(new BlobRenderer());
	}
	private void createObstacle() {
		for (int i = 0;i < 10;i++) {
			Entity obs = new Entity(new Rectangle(800, MathUtils.random(0, 600 - 128), 32, 128));
			obs.setComponent(RailPosition.class, new RailPosition());
			for (int r = 0;r < i + 1;r++)
				obs.getComponent(RailPosition.class).increment(800);
			obs.setComponent(RailObstacle.class, new RailObstacle(false, 300, 400));
			obs.setComponent(DangerousObstacle.class, new DangerousObstacle());
			obs.setComponent(Extended9Patch.class, new Extended9Patch((Texture) game.assets.get("Obstacles.png"), 32, 0, 0, 32, 0, 0, 32, 32, 32));
			engine.addEntity(obs);
		}
	}
	private void createPlayer() {
		dummy = new Entity(new Rectangle(0, 100, 32, 32));
		dummy.setComponent(BlobSprite.class, new BlobSprite((Texture) game.assets.get("BlobFull.png")));
		dummy.setComponent(Weight.class, new Weight(2));
		dummy.setComponent(FallingSpeed.class, new FallingSpeed());
		dummy.setComponent(RailPosition.class, new RailPosition());
		dummy.setComponent(BouncinessComponent.class, new BouncinessComponent(2f / 3));
//		dummy.setComponent(RailObstacle.class, new RailObstacle(false, 0, 50));
		dummy.setComponent(RailVelocity.class, new RailVelocity());
		dummy.setComponent(WeightGrowth.class, new WeightGrowth(new BlobGrowthController(dummy)));
		engine.addEntity(dummy);
	}
	public Engine getEngine() {
		return engine;
	}
	@Override
	public void show() {
		stored.set(game.batch.getProjectionMatrix());
		theme.setLooping(true);
		theme.setVolume(0.5f);
		theme.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		engine.act(delta);
		game.batch.begin();
		engine.draw(game.batch, game.shapes);
		game.batch.end();
		if (engine.failed())
			game.fail();
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
	}

	@Override
	public void dispose() {
	}

}
