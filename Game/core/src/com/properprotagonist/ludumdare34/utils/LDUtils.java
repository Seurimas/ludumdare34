package com.properprotagonist.ludumdare34.utils;

import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.blob.Burst;
import com.properprotagonist.ludumdare34.ecs.gravity.FallingSpeed;
import com.properprotagonist.ludumdare34.ecs.rail.RailVelocity;

public class LDUtils {
	public static Class<? extends Component>[] componentArray(Class<? extends Component>... components) {
		return components;
	}
	public static float getVelocityX(Entity e) {
		float velocityX = 0;
		if (e.hasComponents(RailVelocity.class))
			velocityX += e.getComponent(RailVelocity.class).getVelocity();
		if (e.hasComponents(Burst.class))
			velocityX += e.getComponent(Burst.class).getModifier();
		return velocityX;
	}
	public static float getVelocityY(Entity e) {
		float velocityY = 0;
		if (e.hasComponents(FallingSpeed.class))
			velocityY += e.getComponent(FallingSpeed.class).vy;
		return velocityY;
	}
}
