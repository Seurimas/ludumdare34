package com.properprotagonist.ludumdare34.ecs.gravity;

import java.util.LinkedList;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups.AntiGrav;
import com.properprotagonist.ludumdare34.ecs.Entity;

public class GravitySystem implements ComponentSystem {
	private float G;
	private float MAX_FALL_SPEED;
	public GravitySystem(float G, float MAX_FALL_SPEED) {
		this.G = G;
		this.MAX_FALL_SPEED = MAX_FALL_SPEED;
	}
	private Class<? extends Component>[] dependencies = new Class[] {
			Weight.class,
			FallingSpeed.class
	};
	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}
	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		for (Entity entity : entities) {
			Weight entityWeight = entity.getComponent(Weight.class);
			FallingSpeed velocity = entity.getComponent(FallingSpeed.class);
			if (entity.hasComponents(AntiGrav.class)) {
				velocity.vy += entityWeight.factor * delta * G;
				velocity.vy = Math.min(velocity.vy, -MAX_FALL_SPEED);
			} else {
				velocity.vy -= entityWeight.factor * delta * G;
				velocity.vy = Math.max(velocity.vy, MAX_FALL_SPEED);
			}
			entity.getBounding().y += velocity.vy;
		}
	}
}
