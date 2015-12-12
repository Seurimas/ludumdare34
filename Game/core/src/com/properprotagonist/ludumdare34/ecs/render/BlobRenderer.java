package com.properprotagonist.ludumdare34.ecs.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;
import com.properprotagonist.ludumdare34.ecs.gravity.Weight;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class BlobRenderer implements RenderSystem {
	public static class BlobSprite implements Component {
		Texture texture;
		public BlobSprite(Texture texture) {
			this.texture = texture;
		}
	}
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(
			BlobSprite.class,
			Weight.class
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
			Weight weight = entity.getComponent(Weight.class);
			int frame = (int) (weight.factor - 1);
			if (frame > 4)
				frame = 4;
			batch.draw(entity.getComponent(BlobSprite.class).texture, 
					bounds.x, bounds.y, bounds.width, bounds.height,
					0, (frame + 1) / 8f,
					1, frame / 8f);
		}
	}

}
