package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.debug.DebugEntityRenderer;
import com.properprotagonist.ludumdare34.debug.DebugRailObstacleRenderer;
import com.properprotagonist.ludumdare34.debug.DebugRailRenderer;
import com.properprotagonist.ludumdare34.debug.DebugWeightRenderer;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.gravity.BouncinessSystem;
import com.properprotagonist.ludumdare34.ecs.gravity.FallingSpeed;
import com.properprotagonist.ludumdare34.ecs.gravity.FloorBounceSystem;
import com.properprotagonist.ludumdare34.ecs.gravity.GravitySystem;
import com.properprotagonist.ludumdare34.ecs.gravity.GrowthDetector;
import com.properprotagonist.ludumdare34.ecs.gravity.GrowthSystem;
import com.properprotagonist.ludumdare34.ecs.gravity.Weight;
import com.properprotagonist.ludumdare34.ecs.gravity.WeightGrowth;
import com.properprotagonist.ludumdare34.ecs.rail.RailObstacle;
import com.properprotagonist.ludumdare34.ecs.rail.RailPosition;
import com.properprotagonist.ludumdare34.ecs.rail.RailTargetCamera;
import com.properprotagonist.ludumdare34.ecs.rail.RailVelocity;
import com.properprotagonist.ludumdare34.ecs.rail.systems.RailMovementSystem;
import com.properprotagonist.ludumdare34.ecs.rail.systems.RailPositioningSystem;
import com.properprotagonist.ludumdare34.ecs.rail.systems.RailScopeSystem;
import com.properprotagonist.ludumdare34.ecs.rail.systems.SingleRailObstacleSystem;

public class MainScreen implements Screen {
	LudumDare34 game;
	Engine engine;
	Entity dummy;
	OrthographicCamera camera;
	public MainScreen(LudumDare34 game) {
		this.game = game;
		engine = new Engine();
		camera = new OrthographicCamera(800, 600);
		createPlayer();
		createObstacle();
		engine.addRenderer(new RailTargetCamera(dummy, camera));
		// RAILS
		engine.addComponentSystem(new RailMovementSystem());
		engine.addComponentSystem(new RailPositioningSystem());
		engine.addComponentSystem(new SingleRailObstacleSystem());
		engine.addComponentSystem(new RailScopeSystem(dummy));
		// GRAVITY
		engine.addComponentSystem(new FloorBounceSystem());
		engine.addComponentSystem(new GravitySystem(5, -15));
		engine.addComponentSystem(new GrowthSystem());
		engine.addComponentSystem(new BouncinessSystem(6));
		engine.addComponentSystem(new FloorBounceSystem());
		
		
		engine.addRenderer(new DebugWeightRenderer());
		engine.addRenderer(new DebugEntityRenderer());
		engine.addRenderer(new DebugRailRenderer());
		engine.addRenderer(new DebugRailObstacleRenderer());
	}
	private void createObstacle() {
		Entity obs = new Entity(new Rectangle(0, 300, 50, 100));
		obs.setComponent(RailPosition.class, new RailPosition());
		obs.getComponent(RailPosition.class).increment(800);
		obs.setComponent(RailObstacle.class, new RailObstacle(false, 300, 400));
		engine.addEntity(obs);
	}
	private void createPlayer() {
		dummy = new Entity(new Rectangle(0, 100, 50, 50));
		dummy.setComponent(Weight.class, new Weight(2));
		dummy.setComponent(FallingSpeed.class, new FallingSpeed());
		dummy.setComponent(RailPosition.class, new RailPosition());
		dummy.setComponent(RailObstacle.class, new RailObstacle(false, 0, 50));
		dummy.setComponent(RailVelocity.class, new RailVelocity());
		dummy.setComponent(WeightGrowth.class, new WeightGrowth(new GrowthDetector() {
			@Override
			public float getCurrentGrowthFactor() {
				if (Gdx.input.isKeyPressed(Keys.SPACE))
					return 1;
				else
					return 0;
			}
		}));
		engine.addEntity(dummy);
	}
	public Engine getEngine() {
		return engine;
	}
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		engine.act(delta);
		game.batch.begin();
		engine.draw(game.batch, game.shapes);
		game.batch.end();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
	}

}
