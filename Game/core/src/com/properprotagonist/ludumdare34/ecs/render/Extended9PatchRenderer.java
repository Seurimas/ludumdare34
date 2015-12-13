package com.properprotagonist.ludumdare34.ecs.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.blob.BlobRenderer.BlobSprite;
import com.properprotagonist.ludumdare34.ecs.gravity.Weight;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class Extended9PatchRenderer implements RenderSystem {
	public static class Extended9Patch implements Component {
		private final Texture texture;
		private final int[] points;
		private Color color = Color.WHITE;
		public Extended9Patch(Texture texture, int... points) {
			this.texture = texture;
			this.points = points;
		}
		public int getTileSize() {
			return (int) points[0];
		}
		public int getXSmall() {
			return points[1];
		}
		public int getYSmall() {
			return points[2];
		}
		public int getXShort() {
			return points[3];
		}
		public int getYShort() {
			return points[4];
		}
		public int getXTall() {
			return points[5];
		}
		public int getYTall() {
			return points[6];
		}
		public int getXLarge() {
			return points[7];
		}
		public int getYLarge() {
			return points[8];
		}
		public void draw(Batch batch, Rectangle rectangle) {
			if (rectangle.width == getTileSize()) {
				if (rectangle.height == getTileSize()) {
					batch.draw(texture, 
							rectangle.x, rectangle.y, 
							getXSmall(), getYSmall(), getTileSize(), getTileSize());
				} else {
					for (int y = 0;y < rectangle.height;y += getTileSize()) {
						int tileOffset;
						if (y == 0)
							tileOffset = getTileSize() * 2;
						else if (y == rectangle.height - getTileSize())
							tileOffset = 0;
						else
							tileOffset = getTileSize();
						batch.draw(texture, 
								rectangle.x, rectangle.y + y,
								getXTall(), getYTall() + tileOffset, getTileSize(), getTileSize());
					}
				}
			} else {
				if (rectangle.height == getTileSize()) {
					for (int x = 0;x < rectangle.width;x += getTileSize()) {
						int tileOffset;
						if (x == 0)
							tileOffset = 0;
						else if (x == rectangle.width - getTileSize())
							tileOffset = getTileSize() * 2;
						else
							tileOffset = getTileSize();
						batch.draw(texture, 
								rectangle.x + x, rectangle.y,
								getXShort() + tileOffset, getYShort(), getTileSize(), getTileSize());
					}
				} else {
					for (int x = 0;x < rectangle.width;x += getTileSize()) {
						int xTileOffset;
						if (x == 0)
							xTileOffset = 0;
						else if (x == rectangle.width - getTileSize())
							xTileOffset = getTileSize() * 2;
						else
							xTileOffset = getTileSize();
						for (int y = 0;y < rectangle.height;y += getTileSize()) {
							int yTileOffset;
							if (y == 0)
								yTileOffset = getTileSize() * 2;
							else if (y == rectangle.height - getTileSize())
								yTileOffset = 0;
							else
								yTileOffset = getTileSize();
							batch.draw(texture, 
									rectangle.x + x, rectangle.y + y,
									getXLarge() + xTileOffset, getYLarge() + yTileOffset, getTileSize(), getTileSize());
						}
					}
				}
			}
		}
		public void setColor(Color green) {
			color = green;
		}
	}
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(
			Extended9Patch.class
			);
	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}

	@Override
	public void draw(Batch batch, ShapeRenderer shapes,
			ComponentEntityList componentEntityList) {
		for (Entity entity : componentEntityList) {
			Extended9Patch patch = entity.getComponent(Extended9Patch.class);
			batch.setColor(patch.color);
			patch.draw(batch, entity.getBounding());
		}
		batch.setColor(Color.WHITE);
	}

}
