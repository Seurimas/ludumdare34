package com.properprotagonist.ludumdare34.ecs.rail.systems;

import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Message;

public class CollisionMessage extends Message {
	public final Entity target, obstacle;
	public CollisionMessage(Entity target, Entity obstacle) {
		this.target = target;
		this.obstacle = obstacle;
	}

}
