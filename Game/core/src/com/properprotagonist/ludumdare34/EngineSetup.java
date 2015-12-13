package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.ShakeCameraSystem.ShakeMessage;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.blob.BlobBits;
import com.properprotagonist.ludumdare34.ecs.blob.BlobBits.PoppedBit;
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
import com.properprotagonist.ludumdare34.ecs.danger.PoppedBlobListener;
import com.properprotagonist.ludumdare34.ecs.danger.RailObstacleSpawner;
import com.properprotagonist.ludumdare34.ecs.gravity.FallingSpeed;
import com.properprotagonist.ludumdare34.ecs.gravity.GravitySystem;
import com.properprotagonist.ludumdare34.ecs.gravity.GrowthSystem;
import com.properprotagonist.ludumdare34.ecs.gravity.Weight;
import com.properprotagonist.ludumdare34.ecs.gravity.WeightGrowth;
import com.properprotagonist.ludumdare34.ecs.menu.TargetListener;
import com.properprotagonist.ludumdare34.ecs.powerups.PowerupRenderer;
import com.properprotagonist.ludumdare34.ecs.powerups.PowerupSystem;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups;
import com.properprotagonist.ludumdare34.ecs.powerups.RailPowerupSpawner;
import com.properprotagonist.ludumdare34.ecs.rail.Collider;
import com.properprotagonist.ludumdare34.ecs.rail.RailTargetCamera;
import com.properprotagonist.ludumdare34.ecs.rail.RailVelocity;
import com.properprotagonist.ludumdare34.ecs.rail.systems.CollisionMessage;
import com.properprotagonist.ludumdare34.ecs.rail.systems.CollisionSystem;
import com.properprotagonist.ludumdare34.ecs.rail.systems.RailMovementSystem;
import com.properprotagonist.ludumdare34.ecs.rail.systems.RailScopeSystem;
import com.properprotagonist.ludumdare34.ecs.render.BackdropRenderer;
import com.properprotagonist.ludumdare34.ecs.render.CenteredTextSystem;
import com.properprotagonist.ludumdare34.ecs.render.DistanceRenderer;
import com.properprotagonist.ludumdare34.ecs.render.Extended9PatchRenderer;
import com.properprotagonist.ludumdare34.ecs.render.StationaryCamera;
import com.properprotagonist.ludumdare34.ecs.render.Extended9PatchRenderer.Extended9Patch;
import com.properprotagonist.ludumdare34.ecs.render.SimpleSpriteRenderer;
import com.properprotagonist.ludumdare34.ecs.toast.QuoteSystem;

public class EngineSetup {
	public static void setupBurst(Engine engine) {
		engine.addComponentSystem(new BurstSystem(burst));
	}
	public static void setupRendering(Engine engine, Entity player, OrthographicCamera camera, Batch uiBatch, boolean rail) {
		if (rail)
			engine.addRenderer(new RailTargetCamera(player, camera));
		else
			engine.addRenderer(new StationaryCamera(player, camera));
		ShakeCameraSystem shake = new ShakeCameraSystem(player, camera);
		engine.addRenderer(shake);
		engine.addListener(ShakeMessage.class, shake);
		engine.addRenderer(new BackdropRenderer(environment, player, rail));
		engine.addRenderer(new QuoteSystem(font));
		engine.addRenderer(new CenteredTextSystem(font));
		engine.addRenderer(new Extended9PatchRenderer());
		engine.addRenderer(new SimpleSpriteRenderer());
		engine.addRenderer(new PowerupRenderer());
		engine.addRenderer(new BlobRenderer());
		if (rail)
			engine.addRenderer(new DistanceRenderer(player, font, uiBatch));
	}
	public static void setupBasicGame(Engine engine, Entity player, boolean powerups) {
		if (powerups)
			engine.addComponentSystem(new RailPowerupSpawner(player, Powerups.powerups));
		engine.addComponentSystem(new RailObstacleSpawner(player, obstacles));
		engine.addComponentSystem(new RailScopeSystem(player));
		if (powerups) {
			engine.addComponentSystem(new PowerupSystem(player));
			Powerups.setup(engine, player);
		}
	}
	private static void setupGravity(Engine engine) {
		engine.addComponentSystem(new GravitySystem(5, -15));
		engine.addComponentSystem(new GrowthSystem());
		engine.addComponentSystem(new FloorSystem(bounce));
		engine.addComponentSystem(new BouncinessSystem(6));
	}
	public static Entity createPlayer(Engine engine, boolean rail) {
		Entity player = new Entity(new Rectangle(0, 100, 32, 32));
		player.setComponent(BlobSprite.class, new BlobSprite(blobTexture));
		player.setComponent(Weight.class, new Weight(2));
		player.setComponent(FallingSpeed.class, new FallingSpeed());
		player.setComponent(BouncinessComponent.class, new BouncinessComponent(2f / 3));
//		dummy.setComponent(RailObstacle.class, new RailObstacle(false, 0, 50));
		if (rail) {
			player.setComponent(RailVelocity.class, new RailVelocity());
			player.getComponent(RailVelocity.class).increaseVelocity(200);
		}
		player.setComponent(WeightGrowth.class, new WeightGrowth(new BlobGrowthController(player)));
		player.setComponent(Burst.class, new Burst(600, 0.5f));
		player.setComponent(Collider.class, new Collider());
		player.setComponent(BlobComponent.class, new BlobComponent());
		engine.addEntity(player);
		return player;
	}
	public static void addCriticalSystems(Engine engine) {
		engine.addComponentSystem(new RailMovementSystem());
		engine.addComponentSystem(new CollisionSystem());
		engine.addListener(CollisionMessage.class, new TargetListener());
		EngineSetup.setupGravity(engine);
		EngineSetup.setupBits(engine);
		EngineSetup.setupBurst(engine);
	}
	public static BlobBits setupBits(Engine engine) {
		BlobBits bits = new BlobBits(environment);
		engine.addListener(CollisionMessage.class, new PoppedBlobListener(pop, bits));
		engine.addListener(CollisionMessage.class, bits);
		engine.addListener(BounceMessage.class, bits);
		return bits;
	}
	private static Sound bounce, burst, pop;
	public static void setSounds(Sound bounce, Sound burst, Sound pop) {
		EngineSetup.bounce = bounce;
		EngineSetup.burst = burst;
		EngineSetup.pop = pop;
	}
	private static Texture environment, obstacles, blobTexture, ui, targets;
	public static void setTextures(Texture environment, Texture obstacles,
			Texture blobTexture, Texture ui, Texture targets) {
		EngineSetup.environment = environment;
		EngineSetup.obstacles = obstacles;
		EngineSetup.blobTexture = blobTexture;
		EngineSetup.ui = ui;
		EngineSetup.targets = targets;
	}
	private static BitmapFont font;
	public static void setFonts(BitmapFont impact) {
		font = impact;
	}
	public static BitmapFont getFont() {
		return font;
	}
	public static Extended9Patch getObstacle9Patch() {
		return new Extended9Patch(obstacles, 32, 0, 0, 32, 0, 0, 32, 32, 32);
	}
	public static Extended9Patch getTarget9Patch() {
		return new Extended9Patch(targets, 10, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	public static Texture getUi() {
		return ui;
	}
}
