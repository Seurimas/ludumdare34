package com.properprotagonist.ludumdare34.ecs.blob;

import com.properprotagonist.ludumdare34.ecs.Component;

public class Burst implements Component {
	private float burstSpeed;
	private float burstProgress;
	private float burstDuration;
	public Burst(float burstSpeed, float burstDuration) {
		this.burstSpeed = burstSpeed;
		this.burstDuration = burstDuration;
		this.burstProgress = burstDuration;
	}
	public boolean isActive() {
		return burstProgress < burstDuration;
	}
	public void start() {
		burstProgress = 0;
	}
	public void update(float delta) {
		burstProgress += delta;
	}
	public float getModifier() {
		if (isActive())
			return burstSpeed;
		else
			return 0;
	}
}
