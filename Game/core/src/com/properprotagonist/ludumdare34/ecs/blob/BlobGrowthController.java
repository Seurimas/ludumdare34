package com.properprotagonist.ludumdare34.ecs.blob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.gravity.GrowthDetector;

public class BlobGrowthController implements GrowthDetector {
	private final Entity blob;
	public BlobGrowthController(Entity blob) {
		this.blob = blob;
	}

	@Override
	public float getCurrentGrowthFactor() {
		if (blob.hasComponents(Burst.class) && blob.getComponent(Burst.class).isActive())
			return 0;
		if (Gdx.input.isKeyPressed(Keys.SPACE))
			return 1;
		else
			return 0;
	}
}
