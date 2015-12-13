package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Message;
import com.properprotagonist.ludumdare34.ecs.MessageListener;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;
import com.properprotagonist.ludumdare34.ecs.blob.Burst;

public class ShakeCameraSystem implements RenderSystem, MessageListener {
	public static class ShakeMessage extends Message {
		private final float duration;
		private final float intensity;
		public ShakeMessage(float duration, float intensity) {
			this.duration = duration;
			this.intensity = intensity;
		}
	}
	private final OrthographicCamera camera;
	private final Entity target;
	public ShakeCameraSystem(Entity target, OrthographicCamera camera) {
		this.target = target;
		this.camera = camera;
	}
	@Override
	public Class<? extends Component>[] dependencies() {
		return new Class[0];
	}
	float screenShakePower = 5f;
	float screenShakeDuration = 0f;
	float screenShakeProgress = 0f;
	float shakeX = 0;
	float shakeY = 0;
	@Override
	public void draw(Batch batch, ShapeRenderer shapes, ComponentEntityList componentEntityList) {
		if (shakeActive()) {
			screenShakeProgress += Gdx.graphics.getDeltaTime();
			float currentPower = screenShakePower * (Math.min(screenShakeDuration, screenShakeProgress) / screenShakeDuration);
			shakeX += MathUtils.random(-0.5f, 0.5f) * currentPower;
			shakeY += MathUtils.random(-0.5f, 0.5f) * currentPower;
			camera.position.x += (int) shakeX;
			camera.position.y += (int) shakeY;
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			shapes.setProjectionMatrix(camera.combined);
		} else {
			screenShakeProgress = screenShakeDuration = 0f;
			shakeX = 0;
			shakeY = 0;
		}
	}
	private boolean shakeActive() {
		return screenShakeProgress < screenShakeDuration;
	}
	@Override
	public void accept(Message message, Engine engine) {
		if (message instanceof ShakeMessage) {
			ShakeMessage shake = (ShakeMessage) message;
			screenShakeProgress = 0;
			screenShakeDuration = shake.duration;
			screenShakePower = shake.intensity;
		}
	}

}
