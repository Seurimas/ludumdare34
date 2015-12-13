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
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups.BurstFast;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups.BurstLong;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups.BurstUp;

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
				burst.start();
				FallingSpeed falling = entity.getComponent(FallingSpeed.class);
				falling.vy = 0;
				if (entity.hasComponents(BurstUp.class))
					falling.vy = entity.getComponent(BurstUp.class).vy;
			}
			float movement = burst.getModifier() * delta;
			float progress = delta;
			if (entity.hasComponents(BurstFast.class))
				movement *= 1.5f;
			if (entity.hasComponents(BurstLong.class))
				progress *= 0.75f;
			entity.move(movement, 0);
			burst.update(progress);
		}
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}
	
}
