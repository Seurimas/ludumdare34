package com.properprotagonist.ludumdare34.ecs.rail.systems;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.rail.RailPosition;
import com.properprotagonist.ludumdare34.ecs.rail.RailVelocity;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class RailMovementSystem implements ComponentSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(
			RailPosition.class,
			RailVelocity.class);

	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		for (Entity entity : entities) {
			RailPosition rPos = entity.getComponent(RailPosition.class);
			RailVelocity rVel = entity.getComponent(RailVelocity.class);
			rPos.increment(rVel.getVelocity());
		}
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}

}
