package com.properprotagonist.ludumdare34.ecs.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;

public class StationaryCamera implements RenderSystem {
	private final OrthographicCamera camera;
	private final Entity target;
	public StationaryCamera(Entity target, OrthographicCamera camera) {
		this.target = target;
		this.camera = camera;
	}
	@Override
	public Class<? extends Component>[] dependencies() {
		return new Class[0];
	}

	@Override
	public void draw(Batch batch, ShapeRenderer shapes, ComponentEntityList componentEntityList) {
		camera.position.x = camera.viewportWidth / 2;
		camera.position.y = camera.viewportHeight / 2;
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shapes.setProjectionMatrix(camera.combined);
	}

}
