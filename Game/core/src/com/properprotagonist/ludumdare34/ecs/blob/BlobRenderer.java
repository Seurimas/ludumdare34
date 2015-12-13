package com.properprotagonist.ludumdare34.ecs.blob;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;
import com.properprotagonist.ludumdare34.ecs.gravity.Weight;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups.AntiGrav;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups.Invulnerable;
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
			if (entity.hasComponents(Burst.class)) {
				Burst burst = entity.getComponent(Burst.class);
				if (burst.isActive())
					frame = 5;
			}
			if (entity.hasComponents(Invulnerable.class))
				batch.setColor(1, 1, 1, 0.5f);
			float v1;
			float v2;
			if (entity.hasComponents(AntiGrav.class)) {
				v1 = frame / 8f;
				v2 = (frame + 1) / 8f;
				frame = Math.max(1, frame);
			} else {
				v1 = (frame + 1) / 8f;
				v2 = frame / 8f;
			}
			batch.draw(entity.getComponent(BlobSprite.class).texture, 
					bounds.x, bounds.y, bounds.width, bounds.height,
					0, v1,
					1, v2);
			if (entity.hasComponents(Invulnerable.class))
				batch.setColor(1, 1, 1, 1);
		}
	}

}
