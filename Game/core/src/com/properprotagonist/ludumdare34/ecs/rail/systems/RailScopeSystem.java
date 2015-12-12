package com.properprotagonist.ludumdare34.ecs.rail.systems;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.rail.RailPosition;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class RailScopeSystem implements ComponentSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(RailPosition.class);
	private final Entity target;
	public RailScopeSystem(Entity target) {
		this.target = target;
	}
	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		RailPosition targetPosition = target.getComponent(RailPosition.class);
		float maxRailPosition = targetPosition.getPosition() - 100;
		for (Entity entity : entities) {
			RailPosition rPos = entity.getComponent(RailPosition.class);
			if (rPos.getPosition() < maxRailPosition)
				engine.removeEntity(entity);
		}
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}

}
