package com.properprotagonist.ludumdare34.ecs.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.render.SimpleSpriteRenderer.SimpleSprite;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class PowerupRenderer implements RenderSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(
			Powerup.class
			);
	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}
	private float duration = 0;
	@Override
	public void draw(Batch batch, ShapeRenderer shapes,
			ComponentEntityList entities) {
		float delta = Gdx.graphics.getDeltaTime();
		duration += delta;
		while (duration > 1)
			duration -= 1;
		batch.setColor(1, 1, 1, 0.5f + (duration > 0.5f ? 1 - duration : duration));
		for (Entity entity : entities) {
			Powerup powerup = entity.getComponent(Powerup.class);
			Rectangle region = entity.getBounding();
			for (int x = (int) region.x;x < region.x + region.width;x += powerup.texture.getRegionWidth()) {
				if (region.y > 300)
					batch.draw(powerup.texture, x, region.y + region.height, 
							powerup.texture.getRegionWidth(), -powerup.texture.getRegionHeight() * 2);
				else
					batch.draw(powerup.texture, x, region.y, powerup.texture.getRegionWidth(), powerup.texture.getRegionHeight() * 2);
				System.out.println(String.format("%d, %f", x, region.y));
			}
		}
		batch.setColor(1, 1, 1, 1);
	}

}
