package com.properprotagonist.ludumdare34.ecs.gravity;

import com.properprotagonist.ludumdare34.ecs.Component;

public class BounceComponent implements Component {
	private float fallingSpeed;
	public BounceComponent(float fallingSpeed) {
		this.fallingSpeed = fallingSpeed;
	}
	public float getRebound(float minimumBounce) {
		float bounce = (-fallingSpeed * 2) / 3;
		if (bounce < minimumBounce)
			return minimumBounce;
		else
			return bounce;
	}
}
