package com.properprotagonist.ludumdare34.ecs.powerups;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Entity;

public abstract class Powerup implements Component {
	public TextureRegion texture;
	public Powerup() {
		
	}
	public abstract void applyToPlayer(Entity player);
	public void setTextureRegion(TextureRegion texture) {
		this.texture = texture;
	}
}
