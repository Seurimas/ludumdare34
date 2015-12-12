package com.properprotagonist.ludumdare34.ecs.gravity;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.rail.RailObstacle;

public class FloorBounceSystem implements ComponentSystem {
	public static final float FLOOR = 25f;
	private Class<? extends Component>[] dependencies = new Class[] {
			FallingSpeed.class
	};
	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		for (Entity entity : entities) {
			FallingSpeed falling = entity.getComponent(FallingSpeed.class);
			if (entity.getBounding().y <= FLOOR && falling.vy < 0)
				bounce(entity, falling);
		}
	}

	private void bounce(Entity entity, FallingSpeed falling) {
		if (entity.hasComponents(BounceComponent.class))
			return;
		BounceComponent bounciness = new BounceComponent(falling.vy);
		entity.setComponent(BounceComponent.class, bounciness);
		entity.getBounding().y = FLOOR;
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}
}
