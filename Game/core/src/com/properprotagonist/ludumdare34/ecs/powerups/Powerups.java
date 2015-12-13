package com.properprotagonist.ludumdare34.ecs.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;

public class Powerups {
	public static class Invulnerable extends PowerupComponent {
		public Invulnerable(float duration) {
			super(duration);
		}
	}
	public static final Powerup INVULN = new Powerup() {
		@Override
		public void applyToPlayer(Entity player) {
			player.setComponent(Invulnerable.class, new Invulnerable(3));
		}
	};
	public static final Powerup[] powerups = new Powerup[] {
		INVULN
	};
	public static void initialize(Texture environmentTextures) {
		INVULN.setTextureRegion(new TextureRegion(environmentTextures, 0, 110, 32, 3));
//		INVULN.setTextureRegion(new TextureRegion(environmentTextures, 0, 113, 32, 3));
//		INVULN.setTextureRegion(new TextureRegion(environmentTextures, 0, 116, 32, 3));
//		INVULN.setTextureRegion(new TextureRegion(environmentTextures, 0, 119, 32, 3));
//		INVULN.setTextureRegion(new TextureRegion(environmentTextures, 0, 122, 32, 3));
//		INVULN.setTextureRegion(new TextureRegion(environmentTextures, 0, 125, 32, 3));
	}
	public static void setup(Engine engine, Entity player) {
		engine.addComponentSystem(new PowerupTrackerSystem<Invulnerable>(Invulnerable.class, player));
	}
}
