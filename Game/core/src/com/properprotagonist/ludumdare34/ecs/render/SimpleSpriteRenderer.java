package com.properprotagonist.ludumdare34.ecs.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class SimpleSpriteRenderer implements RenderSystem {
	public static class SimpleSprite implements Component {
		TextureRegion texture;
		public SimpleSprite(TextureRegion texture) {
			this.texture = texture;
		}
	}
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(
			SimpleSprite.class
			);
	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}

	@Override
	public void draw(Batch batch, ShapeRenderer shapes,
			ComponentEntityList componentEntityList) {
		for (Entity entity : componentEntityList) {
			Rectangle bounds = entity.getBounding();
			batch.draw(entity.getComponent(SimpleSprite.class).texture, bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}

}
