package com.properprotagonist.ludumdare34.ecs.rail.systems;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.rail.RailObstacle;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class SingleRailObstacleSystem implements ComponentSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(
			RailObstacle.class);

	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		for (Entity entity : entities) {
			RailObstacle rObs = entity.getComponent(RailObstacle.class);
			rObs.offset(entity.getBounding().y);
		}
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}

}
