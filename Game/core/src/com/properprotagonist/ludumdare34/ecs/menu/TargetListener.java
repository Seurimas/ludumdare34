package com.properprotagonist.ludumdare34.ecs.menu;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Message;
import com.properprotagonist.ludumdare34.ecs.MessageListener;
import com.properprotagonist.ludumdare34.ecs.blob.BlobBits;
import com.properprotagonist.ludumdare34.ecs.blob.BlobComponent;
import com.properprotagonist.ludumdare34.ecs.blob.BlobRenderer.BlobSprite;
import com.properprotagonist.ludumdare34.ecs.blob.Burst;
import com.properprotagonist.ludumdare34.ecs.bounce.BouncinessComponent;
import com.properprotagonist.ludumdare34.ecs.gravity.FallingSpeed;
import com.properprotagonist.ludumdare34.ecs.gravity.Weight;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups.Invulnerable;
import com.properprotagonist.ludumdare34.ecs.rail.Collider;
import com.properprotagonist.ludumdare34.ecs.rail.RailVelocity;
import com.properprotagonist.ludumdare34.ecs.rail.systems.CollisionMessage;
import com.properprotagonist.ludumdare34.ecs.render.SimpleSpriteRenderer.SimpleSprite;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class TargetListener implements MessageListener {
	public TargetListener() {
	}
	@Override
	public void accept(Message message, Engine engine) {
		if (message instanceof CollisionMessage) {
			CollisionMessage collision = (CollisionMessage) message;
			if (collision.obstacle.hasComponents(TargetObstacle.class))
				collision.obstacle.getComponent(TargetObstacle.class).activate(engine, collision.target, collision.obstacle);
		}
	}
	

}
