package com.properprotagonist.ludumdare34.ecs.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.properprotagonist.ludumdare34.LudumDare34;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;

public class BackdropRenderer implements RenderSystem {
	private final TextureRegion floor;
	private final TextureRegion ceiling;
	private final Entity target;
	public BackdropRenderer(Texture texture3Piece, Entity target) {
		ceiling = new TextureRegion(texture3Piece, 0, 0, 32, 32);
		floor = new TextureRegion(texture3Piece, 0, 64, 32, 32);
		this.target = target;
	}
	Vector3 currentLeft = new Vector3();
	@Override
	public Class<? extends Component>[] dependencies() {
		return new Class[0];
	}

	@Override
	public void draw(Batch batch, ShapeRenderer shapes,
			ComponentEntityList componentEntityList) {
		float left = (int)(target.getBounding().x) / 128 * 128 - 128;
		for (int x = 0;x < LudumDare34.SCREEN_WIDTH + 256;x+= 32) {
			batch.draw(floor, x + left, 0);
			batch.draw(ceiling, x + left, LudumDare34.SCREEN_HEIGHT - 32);
		}
	}

}
