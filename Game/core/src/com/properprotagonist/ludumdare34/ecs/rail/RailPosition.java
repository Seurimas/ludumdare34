package com.properprotagonist.ludumdare34.ecs.rail;

import com.properprotagonist.ludumdare34.ecs.Component;

public class RailPosition implements Component {
	public RailPosition() {
		
	}
	private float x;
	public void increment(float val) {
		if (val < 0)
			throw new RuntimeException("THIS IS INCORRECT!");
		else
			x += val;
	}
	public float getPosition() {
		return x;
	}
}
