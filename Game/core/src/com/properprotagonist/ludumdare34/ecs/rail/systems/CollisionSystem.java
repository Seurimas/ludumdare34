package com.properprotagonist.ludumdare34.ecs.rail.systems;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.rail.Collider;
import com.properprotagonist.ludumdare34.ecs.rail.Obstacle;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class CollisionSystem implements ComponentSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray();
	public CollisionSystem() {
	}
	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		for (Entity collider : entities) {
			if (!collider.hasComponents(Collider.class))
				continue;
			for (Entity entity : entities) {
				if (!entity.hasComponents(Obstacle.class))
					continue;
				if (entity.collides(collider))
					engine.handleMessage(new CollisionMessage(collider, entity));
			}
		}
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}
	
}
