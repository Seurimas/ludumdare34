package com.properprotagonist.ludumdare34.ecs.rail.systems;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class RailScopeSystem implements ComponentSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray();
	private final Entity target;
	public RailScopeSystem(Entity target) {
		this.target = target;
	}
	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		float maxRailPosition = target.getBounding().x - 100;
		for (Entity entity : entities) {
			if (entity.getBounding().x + entity.getBounding().width < maxRailPosition)
				engine.removeEntity(entity);
		}
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}

}
