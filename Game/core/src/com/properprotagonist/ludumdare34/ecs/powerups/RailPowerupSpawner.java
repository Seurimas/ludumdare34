package com.properprotagonist.ludumdare34.ecs.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.LudumDare34;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.bounce.BouncinessComponent;
import com.properprotagonist.ludumdare34.ecs.bounce.FloorSystem;
import com.properprotagonist.ludumdare34.ecs.gravity.FallingSpeed;
import com.properprotagonist.ludumdare34.ecs.gravity.Weight;
import com.properprotagonist.ludumdare34.ecs.rail.RailObstacle;
import com.properprotagonist.ludumdare34.ecs.render.Extended9PatchRenderer.Extended9Patch;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class RailPowerupSpawner implements ComponentSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray();
	private final Entity target;
	private float lastSpawnX = 2000;
	private float spawnDuration = 1000;
	private float spawnBuffer = 1200;
	private final Powerup[] powerups;
	public RailPowerupSpawner(Entity target, Powerup... powerups) {
		this.target = target;
		this.powerups = powerups;
	}
	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		if (target.getBounding().x > lastSpawnX + spawnDuration) {
			float spawnTargetX = lastSpawnX + spawnDuration;
			Entity spawned = null;
			int spawnCount = 1;
			while (!acceptSpawn(spawned) || spawnCount > 5) {
				spawned = getNewObstacle(engine, spawnTargetX);
			}
			engine.spawnEntity(spawned);
			lastSpawnX += spawnDuration + spawned.getBounding().width;
		}
	}

	private boolean acceptSpawn(Entity spawned) {
		if (spawned == null)
			return false;
		if (spawned.getBounding().y < LudumDare34.SCREEN_HEIGHT / 2 != target.getBounding().y < LudumDare34.SCREEN_HEIGHT) {
			return MathUtils.randomBoolean(); // Reject half the off-side spawns.
		} else {
			return true;
		}
	}
	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}
	private Entity getNewObstacle(Engine engine, float x) {
		Rectangle bounds = new Rectangle(x + spawnBuffer, FloorSystem.FLOOR, 256, 16);
		Entity obs = new Entity(bounds);
		if (MathUtils.randomBoolean(0.5f)) {
			bounds.y = FloorSystem.CEILING - bounds.height;
		}
		obs.setComponent(RailObstacle.class, new RailObstacle());
		obs.setComponent(Powerup.class, getRandomPowerup());
		return obs;
	}
	
	private Powerup getRandomPowerup() {
		return powerups[MathUtils.random(powerups.length - 1)];
	}

}
