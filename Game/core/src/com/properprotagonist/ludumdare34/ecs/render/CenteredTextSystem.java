package com.properprotagonist.ludumdare34.ecs.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

public class CenteredTextSystem implements RenderSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(
			CenteredText.class);
	private static class CenteredText implements Component {
		private final String value;
		private final Color color;
		public CenteredText(String value, Color color) {
			this.value = value;
			this.color = color;
		}
	}
	BitmapFont font;
	public CenteredTextSystem(BitmapFont font) {
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
			CenteredText quote = entity.getComponent(CenteredText.class);
			font.setColor(quote.color);
			GlyphLayout layout = new GlyphLayout(font, quote.value);
			font.draw(batch, quote.value, 
					entity.getBounding().x + entity.getBounding().width / 2 - layout.width / 2, 
					entity.getBounding().y + entity.getBounding().height / 2 + layout.height / 2);
		}
	}
	public static void speak(Entity speaker, String words, Color color) {
		speaker.setComponent(CenteredText.class, new CenteredText(words, color));
	}
}
