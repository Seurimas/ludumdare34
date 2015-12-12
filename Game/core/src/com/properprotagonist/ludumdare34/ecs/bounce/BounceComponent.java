package com.properprotagonist.ludumdare34.ecs.bounce;

import com.properprotagonist.ludumdare34.ecs.Component;

public class BounceComponent implements Component {
	private float fallingSpeed;
	private BouncinessComponent bounciness;
	public BounceComponent(float fallingSpeed, BouncinessComponent bounciness) {
		this.fallingSpeed = fallingSpeed;
		this.bounciness = bounciness;
	}
	public float getRebound(float minimumBounce) {
		float bounce = -fallingSpeed * bounciness.getFactor();;
		if (Math.abs(bounce) < minimumBounce) {
			if (bounce < 0)
				return -minimumBounce;
			else
				return minimumBounce;
		} else
			return bounce;
	}
}
