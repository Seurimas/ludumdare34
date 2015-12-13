package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
	public MainScreen(LudumDare34 game) {
		this.game = game;
		engine = new Engine();
		camera = new OrthographicCamera(800, 600);
		theme = game.assets.get("main_theme.wav");
		createPlayer();
		// RAILS
		engine.addComponentSystem(new RailMovementSystem());
		engine.addComponentSystem(new CollisionSystem());
		// GRAVITY
		Sound bounce = game.assets.get("bounce.wav", Sound.class);
		setupGravity(bounce);
		// DANGER
		BlobBits bits = new BlobBits(game.assets.get("FloorAndCeiling.png", Texture.class));
		engine.addListener(CollisionMessage.class, new PoppedBlobListener(game.assets.get("pop.wav", Sound.class),
				bits));
		engine.addListener(CollisionMessage.class, bits);
		engine.addListener(BounceMessage.class, bits);
		
		// BLOB
		Texture obstacles = game.assets.get("Obstacles.png");
		Texture environment = game.assets.get("FloorAndCeiling.png", Texture.class);
		engine.addComponentSystem(new BurstSystem());
		setupBasicGame(obstacles, environment);
		
//		engine.addRenderer(new DebugWeightRenderer());
//		engine.addRenderer(new DebugEntityRenderer());
		BitmapFont font = game.assets.get("impact.fnt", BitmapFont.class);
		engine.addRenderer(new BackdropRenderer(environment, player));
		engine.addRenderer(new QuoteSystem(font));
		engine.addRenderer(new DistanceRenderer(player, font, game.uiBatch));
		engine.addRenderer(new Extended9PatchRenderer());
		engine.addRenderer(new SimpleSpriteRenderer());
		engine.addRenderer(new PowerupRenderer());
		engine.addRenderer(new BlobRenderer());
	}
	private void setupBasicGame(Texture obstacles, Texture environment) {
		engine.addComponentSystem(new RailPowerupSpawner(player, Powerups.powerups));
		engine.addComponentSystem(new RailObstacleSpawner(player, obstacles));
		engine.addRenderer(new RailTargetCamera(player, camera));
		engine.addComponentSystem(new RailScopeSystem(player));
		engine.addComponentSystem(new PowerupSystem(player));
		Powerups.initialize(environment);
		Powerups.setup(engine, player);
	}
	private void setupGravity(Sound bounce) {
		engine.addComponentSystem(new GravitySystem(5, -15));
		engine.addComponentSystem(new GrowthSystem());
		engine.addComponentSystem(new FloorSystem(bounce));
		engine.addComponentSystem(new BouncinessSystem(6));
	}
	private void createPlayer() {
		player = new Entity(new Rectangle(0, 100, 32, 32));
		player.setComponent(BlobSprite.class, new BlobSprite((Texture) game.assets.get("BlobFull.png")));
		player.setComponent(Weight.class, new Weight(2));
		player.setComponent(FallingSpeed.class, new FallingSpeed());
		player.setComponent(BouncinessComponent.class, new BouncinessComponent(2f / 3));
//		dummy.setComponent(RailObstacle.class, new RailObstacle(false, 0, 50));
		player.setComponent(RailVelocity.class, new RailVelocity());
		player.getComponent(RailVelocity.class).increaseVelocity(200);
		player.setComponent(WeightGrowth.class, new WeightGrowth(new BlobGrowthController(player)));
		player.setComponent(Burst.class, new Burst(600, 0.5f));
		player.setComponent(Collider.class, new Collider());
		player.setComponent(BlobComponent.class, new BlobComponent());
		engine.addEntity(player);
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
		theme.stop();
	}

}
