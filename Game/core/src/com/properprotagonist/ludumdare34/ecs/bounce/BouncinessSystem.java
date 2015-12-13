package com.properprotagonist.ludumdare34.ecs.bounce;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.gravity.FallingSpeed;
import com.properprotagonist.ludumdare34.ecs.rail.Obstacle;

public class BouncinessSystem implements ComponentSystem {
	private final float minimumBounce;
	public BouncinessSystem(float minimumBounce) {
		this.minimumBounce = minimumBounce;
	}
	private Class<? extends Component>[] dependencies = new Class[] {
			FallingSpeed.class,
			BounceComponent.class
	};

	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		for (Entity entity : entities) {
			FallingSpeed falling = entity.getComponent(FallingSpeed.class);
			BounceComponent bounce = entity.getComponent(BounceComponent.class);
			falling.vy = bounce.getRebound(minimumBounce);
			entity.removeComponent(BounceComponent.class);
		}
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}

}
