package com.properprotagonist.ludumdare34.ecs.rail.systems;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.rail.RailPosition;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class RailPositioningSystem implements ComponentSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(RailPosition.class);

	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		for (Entity entity : entities) {
			RailPosition rPos = entity.getComponent(RailPosition.class);
			entity.getBounding().x = rPos.getPosition() - entity.getBounding().width / 2;
		}
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}

}
