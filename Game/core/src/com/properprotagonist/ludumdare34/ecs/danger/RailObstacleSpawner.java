package com.properprotagonist.ludumdare34.ecs.danger;

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

public class RailObstacleSpawner implements ComponentSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray();
	private final Entity target;
	private float lastSpawnX = 400;
	private float spawnDuration = 300;
	private float spawnBuffer = 1200;
	private final Texture[] textures;
	public RailObstacleSpawner(Entity target, Texture... textures) {
		this.target = target;
		this.textures = textures;
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
		Rectangle bounds = new Rectangle(x + spawnBuffer, MathUtils.random(0, 600 - 128), 32, (MathUtils.random(2) + 2) * 32);
		Entity obs = new Entity(bounds);
		if (MathUtils.randomBoolean(0.25f)) {
			float t = bounds.width;
			bounds.width = bounds.height;
			bounds.height = t;
			if (MathUtils.randomBoolean())
				bounds.y = FloorSystem.FLOOR;
			else if (MathUtils.randomBoolean())
				bounds.y = FloorSystem.CEILING - bounds.height;
		} else if (x > 3000 && MathUtils.randomBoolean(0.25f)) {
			bounds.width = bounds.height = 32;
			obs.setComponent(BouncinessComponent.class, new BouncinessComponent(1.25f));
			obs.setComponent(Weight.class, new Weight(1));
			obs.setComponent(FallingSpeed.class, new FallingSpeed());
		}
		if (x > 4000 && MathUtils.randomBoolean(0.5f)) {
			bounds.width += 32;
		}
		if (x > 4000 && MathUtils.randomBoolean(0.5f)) {
			bounds.height += 32;
		}
		obs.setComponent(RailObstacle.class, new RailObstacle());
		obs.setComponent(DangerousObstacle.class, new DangerousObstacle());
		obs.setComponent(Extended9Patch.class, getRandomTexture());
		return obs;
	}
	private Extended9Patch getRandomTexture() {
		return new Extended9Patch(textures[MathUtils.random(textures.length - 1)], 32, 0, 0, 32, 0, 0, 32, 32, 32);
	}

}
