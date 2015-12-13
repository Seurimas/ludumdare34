package com.properprotagonist.ludumdare34.ecs.toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups.Invulnerable;
import com.properprotagonist.ludumdare34.ecs.rail.RailVelocity;
import com.properprotagonist.ludumdare34.ecs.rail.systems.CollisionMessage;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Message;
import com.properprotagonist.ludumdare34.ecs.MessageListener;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class QuoteSystem implements RenderSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(
			Quote.class);
	private static class Quote implements Component {
		private final String value;
		private final float duration;
		private final Color color;
		private float progress;
		public Quote(String value, Color color, float duration) {
			this.value = value;
			this.color = color;
			this.duration = duration;
			progress = 0;
		}
	}
	BitmapFont font;
	public QuoteSystem(BitmapFont font) {
		this.font = font;
	}
	Engine engine = null;
	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}
	@Override
	public void draw(Batch batch, ShapeRenderer shapes,
			ComponentEntityList entities) {
		for (Entity entity : entities) {
			Quote quote = entity.getComponent(Quote.class);
			font.setColor(quote.color);
			font.draw(batch, quote.value, entity.getBounding().x, entity.getBounding().y + entity.getBounding().height + font.getLineHeight());
			quote.progress += Gdx.graphics.getDeltaTime();
			if (quote.progress > quote.duration)
				entity.removeComponent(Quote.class);
		}
	}
	public static final Color POWERUP = Color.PURPLE;
	public static final Color BURST = Color.BLUE;
	public static void powerup(Entity speaker, String words) {
		speak(speaker, words, POWERUP, 3);
	}
	public static void whee(Entity speaker) {
		speak(speaker, "WHEEEEE!", BURST, 1);
	}
	public static void speak(Entity speaker, String words, Color color, float duration) {
		speaker.setComponent(Quote.class, new Quote(words, color, duration));
	}
}
