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

public class PowerupSystem implements ComponentSystem, MessageListener {
	Entity player;
	public PowerupSystem(Entity player) {
		this.player = player;
	}
	@Override
	public void accept(Message message, Engine engine) {
		if (message instanceof CollisionMessage) {
			CollisionMessage collision = (CollisionMessage) message;
			if (collision.obstacle.hasComponents(Powerup.class)) {
				collision.obstacle.getComponent(Powerup.class).applyToPlayer(player);
				collision.obstacle.removeComponent(Powerup.class);
			}
		}
	}

	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return LDUtils.componentArray();
	}
	
}
