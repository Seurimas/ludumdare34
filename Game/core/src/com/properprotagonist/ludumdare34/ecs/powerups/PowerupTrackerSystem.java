package com.properprotagonist.ludumdare34.ecs.powerups;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups.Invulnerable;
import com.properprotagonist.ludumdare34.ecs.rail.systems.CollisionMessage;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Message;
import com.properprotagonist.ludumdare34.ecs.MessageListener;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class  PowerupTrackerSystem<T extends PowerupComponent> implements ComponentSystem {
	Entity player;
	private Class<T> clazz;
	public PowerupTrackerSystem(Class<T> clazz, Entity player) {
		this.player = player;
		this.clazz = clazz;
	}

	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		if (player.hasComponents(clazz)) {
			T powerup = player.getComponent(clazz);
			powerup.update(delta);
			if (powerup.isComplete())
				player.removeComponent(clazz);
		}
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return LDUtils.componentArray();
	}
	
}
