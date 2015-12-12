package com.properprotagonist.ludumdare34.ecs.bounce;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.gravity.FallingSpeed;
import com.properprotagonist.ludumdare34.ecs.rail.RailObstacle;

public class FloorSystem implements ComponentSystem {
	public static final float FLOOR = 25f;
	public static final float CEILING = 575f;
	private Class<? extends Component>[] dependencies = new Class[] {
			FallingSpeed.class
	};
	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		for (Entity entity : entities) {
			FallingSpeed falling = entity.getComponent(FallingSpeed.class);
			if (entity.getBounding().y <= FLOOR && falling.vy < 0)
				bounce(entity, falling, true);
			else if (entity.getBounding().y + entity.getBounding().height >= CEILING && falling.vy > 0)
				bounce(entity, falling, false);
		}
	}

	private void bounce(Entity entity, FallingSpeed falling, boolean floor) {
		if (entity.hasComponents(BounceComponent.class))
			return;
		if (entity.hasComponents(BouncinessComponent.class)) {
			BouncinessComponent bounciness = entity.getComponent(BouncinessComponent.class);
			BounceComponent bounce = new BounceComponent(falling.vy, bounciness);
			entity.setComponent(BounceComponent.class, bounce);
		}
		if (floor)
			entity.getBounding().y = FLOOR;
		else
			entity.getBounding().y = CEILING - entity.getBounding().height;
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}
}
