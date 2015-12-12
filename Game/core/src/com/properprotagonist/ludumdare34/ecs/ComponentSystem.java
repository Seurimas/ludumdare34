package com.properprotagonist.ludumdare34.ecs;

import java.util.LinkedList;

import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;

public interface ComponentSystem {

	void act(float delta,  ComponentEntityList entities, Engine engine);
	Class<? extends Component>[] dependencies();
}
