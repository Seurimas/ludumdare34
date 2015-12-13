package com.properprotagonist.ludumdare34.ecs.bounce;

import com.badlogic.gdx.audio.Sound;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.blob.BlobBits;
import com.properprotagonist.ludumdare34.ecs.blob.BlobComponent;
import com.properprotagonist.ludumdare34.ecs.blob.BlobBits.PoppedBit;
import com.properprotagonist.ludumdare34.ecs.gravity.FallingSpeed;
import com.properprotagonist.ludumdare34.ecs.rail.Obstacle;

public class FloorSystem implements ComponentSystem {
	public static final float FLOOR = 31;
	public static final float CEILING = 550;
	private Class<? extends Component>[] dependencies = new Class[] {
			FallingSpeed.class
	};
	private final Sound sound;
	public FloorSystem(Sound sound) {
		this.sound = sound;
	}

	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		for (Entity entity : entities) {
			FallingSpeed falling = entity.getComponent(FallingSpeed.class);
			boolean bounced = false;
			if (entity.getBounding().y <= FLOOR && falling.vy < 0)
				bounced = bounce(entity, falling, true);
			else if (entity.getBounding().y + entity.getBounding().height >= CEILING && falling.vy > 0)
				bounced = bounce(entity, falling, false);
			if (bounced) {
				engine.handleMessage(new BounceMessage(entity, falling.vy));
			}
		}
	}

	private boolean bounce(Entity entity, FallingSpeed falling, boolean floor) {
		if (entity.hasComponents(BounceComponent.class))
			return false;
		boolean bounced = false;
		if (entity.hasComponents(BouncinessComponent.class)) {
			BouncinessComponent bounciness = entity.getComponent(BouncinessComponent.class);
			BounceComponent bounce = new BounceComponent(falling.vy, bounciness);
			entity.setComponent(BounceComponent.class, bounce);
			if (entity.hasComponents(BlobComponent.class))
				sound.play(1, 1f, -1);
			else if (entity.hasComponents(PoppedBit.class))
				sound.play(0.25f, 1f, -1);
			else
				sound.play(0.5f, 1.25f, 1);
			bounced = true;
		}
		if (floor)
			entity.getBounding().y = FLOOR;
		else
			entity.getBounding().y = CEILING - entity.getBounding().height;
		return bounced;
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}
}
