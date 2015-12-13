package com.properprotagonist.ludumdare34.ecs.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.toast.QuoteSystem;

public class Powerups {
	/*
	 * INVULNERABILITY
	 */
	public static class Invulnerable extends PowerupComponent {
		public Invulnerable(float duration) {
			super(duration);
		}
	}
	public static final Powerup INVULN = new Powerup() {
		@Override
		public void applyToPlayer(Entity player) {
			player.setComponent(Invulnerable.class, new Invulnerable(5));
			QuoteSystem.powerup(player, "I'm a ghost!");
		}
	};
	/*
	 * BURSTLONG
	 */
	public static class BurstLong extends PowerupComponent {
		public BurstLong(float duration) {
			super(duration);
		}
	}
	public static final Powerup BURSTLONG = new Powerup() {
		@Override
		public void applyToPlayer(Entity player) {
			player.setComponent(BurstLong.class, new BurstLong(5));
			QuoteSystem.powerup(player, "I can fly~~!");
		}
	};
	/*
	 * BURSTLONG
	 */
	public static class BurstFast extends PowerupComponent {
		public BurstFast(float duration) {
			super(duration);
		}
	}
	public static final Powerup BURSTFAST = new Powerup() {
		@Override
		public void applyToPlayer(Entity player) {
			player.setComponent(BurstFast.class, new BurstFast(5));
			QuoteSystem.powerup(player, "Zoom zoom zoom! B)");
		}
	};
	/*
	 * BURSTUP
	 */
	public static class BurstUp extends PowerupComponent {
		public final float vy;

		public BurstUp(float duration, float vy) {
			super(duration);
			this.vy = vy;
		}
	}
	public static final Powerup BURSTUP = new Powerup() {
		@Override
		public void applyToPlayer(Entity player) {
			player.setComponent(BurstUp.class, new BurstUp(5, 6));
			QuoteSystem.powerup(player, "Up up and away ^_^!");
		}
	};
	/*
	 * BURSTUP
	 */
	public static class AntiGrav extends PowerupComponent {
		public AntiGrav(float duration) {
			super(duration);
		}
	}
	public static final Powerup ANTIGRAV = new Powerup() {
		@Override
		public void applyToPlayer(Entity player) {
			player.setComponent(AntiGrav.class, new AntiGrav(5));
			QuoteSystem.powerup(player, "Whoa... whooooa, whoooooooa");
		}
	};
	
	public static final Powerup[] powerups = new Powerup[] {
		INVULN,
		BURSTLONG,
		BURSTFAST,
		BURSTUP,
		ANTIGRAV
	};
	public static void initialize(Texture environmentTextures) {
		INVULN.setTextureRegion(new TextureRegion(environmentTextures, 0, 110, 32, 3));
		BURSTLONG.setTextureRegion(new TextureRegion(environmentTextures, 0, 113, 32, 3));
		BURSTFAST.setTextureRegion(new TextureRegion(environmentTextures, 0, 116, 32, 3));
		BURSTUP.setTextureRegion(new TextureRegion(environmentTextures, 0, 119, 32, 3));
		ANTIGRAV.setTextureRegion(new TextureRegion(environmentTextures, 0, 122, 32, 3));
//		INVULN.setTextureRegion(new TextureRegion(environmentTextures, 0, 125, 32, 3));
	}
	public static void setup(Engine engine, Entity player) {
		engine.addComponentSystem(new PowerupTrackerSystem<Invulnerable>(Invulnerable.class, player));
		engine.addComponentSystem(new PowerupTrackerSystem<BurstLong>(BurstLong.class, player));
		engine.addComponentSystem(new PowerupTrackerSystem<BurstFast>(BurstFast.class, player));
		engine.addComponentSystem(new PowerupTrackerSystem<BurstUp>(BurstUp.class, player));
		engine.addComponentSystem(new PowerupTrackerSystem<AntiGrav>(AntiGrav.class, player));
	}
}
