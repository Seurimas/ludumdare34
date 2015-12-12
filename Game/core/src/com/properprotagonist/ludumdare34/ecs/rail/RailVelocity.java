package com.properprotagonist.ludumdare34.ecs.rail;

import com.properprotagonist.ludumdare34.ecs.Component;

public class RailVelocity implements Component {
	private float vx;
	public RailVelocity() {
		vx = 4;
	}
	public float getVelocity() {
		return vx;
	}
	public void increaseVelocity(float val) {
		vx += val;
	}
	public float friction(float factor) {
		vx *= factor;
		return vx;
	}
}
