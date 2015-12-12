package com.properprotagonist.ludumdare34.ecs.rail.systems;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.rail.RailObstacle;
import com.properprotagonist.ludumdare34.ecs.rail.RailPosition;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class RailCollisionSystem implements ComponentSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(
			RailObstacle.class,
			RailPosition.class
			);
	private final Entity target;
	public RailCollisionSystem(Entity target) {
		this.target = target;
	}
	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		for (Entity entity : entities) {
			RailObstacle obstacle = entity.getComponent(RailObstacle.class);
			
		}
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return null;
	}
	
}
