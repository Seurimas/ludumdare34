package com.properprotagonist.ludumdare34.ecs.gravity;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;

public class GrowthSystem implements ComponentSystem {

	private static final Class<? extends Component>[] dependencies = new Class[] {
		Weight.class,
		WeightGrowth.class
	};

	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		for (Entity entity : entities) {
			Weight currentWeight = entity.getComponent(Weight.class);
			WeightGrowth growth = entity.getComponent(WeightGrowth.class);
			currentWeight.factor += growth.getCurrentGrowth();
			currentWeight.factor = growth.clampWeight(currentWeight.factor);
		}
	}
	
	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}

}
