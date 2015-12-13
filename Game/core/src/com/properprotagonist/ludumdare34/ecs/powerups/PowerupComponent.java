package com.properprotagonist.ludumdare34.ecs.powerups;

import com.badlogic.gdx.math.MathUtils;
import com.properprotagonist.ludumdare34.ecs.Component;

public class PowerupComponent implements Component {
	private final float FLASH_INTERVAL = 0.125f;
	private final float duration;
	private float progress;
	private float flashProgress;
	public PowerupComponent(float duration) {
		this.duration = duration;
		this.progress = 0;
		this.flashProgress = 0;
	}
	public void update(float delta) {
		progress += delta;
		flashProgress += delta;
	}
	public boolean isComplete() {
		return progress > duration;
	}
	public boolean needFlash() {
		if (flashProgress > FLASH_INTERVAL) {
			flashProgress -= FLASH_INTERVAL;
			return true;
		} else
			return false;
	}
	public float flashFactor() {
		return MathUtils.clamp(flashProgress / FLASH_INTERVAL, 0, 1);
	}
}
