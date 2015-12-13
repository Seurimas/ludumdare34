package com.properprotagonist.ludumdare34.ecs.blob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.bounce.BounceComponent;
import com.properprotagonist.ludumdare34.ecs.gravity.FallingSpeed;

public class BurstSystem implements ComponentSystem {
	private Class<? extends Component>[] dependencies = new Class[] {
			FallingSpeed.class,
			Burst.class
	};

	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		for (Entity entity : entities) {
			Burst burst = entity.getComponent(Burst.class);
			if (!burst.isActive() && Gdx.input.isKeyJustPressed(Keys.ENTER)) {
				FallingSpeed falling = entity.getComponent(FallingSpeed.class);
				falling.vy = 0;
				burst.start();
			}
			entity.move(burst.getModifier() * delta, 0);
			burst.update(delta);
		}
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}
	
}
