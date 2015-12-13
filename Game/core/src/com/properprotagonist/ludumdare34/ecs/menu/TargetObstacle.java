package com.properprotagonist.ludumdare34.ecs.menu;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;

public abstract class TargetObstacle implements Component {
	public abstract void activate(Engine engine, Entity activater, Entity obstacle);
}
