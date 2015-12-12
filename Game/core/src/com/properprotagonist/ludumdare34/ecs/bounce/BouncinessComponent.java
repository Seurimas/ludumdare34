package com.properprotagonist.ludumdare34.ecs.bounce;

import com.properprotagonist.ludumdare34.ecs.Component;

public class BouncinessComponent implements Component {
	private float factor;
	public float getFactor() {
		return factor;
	}
	public BouncinessComponent(float factor) {
		this.factor = factor;
	}
}
