package com.properprotagonist.ludumdare34.ecs.blob;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Message;
import com.properprotagonist.ludumdare34.ecs.MessageListener;
import com.properprotagonist.ludumdare34.ecs.bounce.BouncinessComponent;
import com.properprotagonist.ludumdare34.ecs.danger.DangerousObstacle;
import com.properprotagonist.ludumdare34.ecs.gravity.FallingSpeed;
import com.properprotagonist.ludumdare34.ecs.gravity.Weight;
import com.properprotagonist.ludumdare34.ecs.rail.Collider;
import com.properprotagonist.ludumdare34.ecs.rail.RailVelocity;
import com.properprotagonist.ludumdare34.ecs.rail.systems.CollisionMessage;
import com.properprotagonist.ludumdare34.ecs.render.SimpleSpriteRenderer.SimpleSprite;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class BlobBits implements MessageListener {
	public static class PoppedBit implements Component {
		
	}
	private final Texture texture;
	public BlobBits(Texture texture) {
		this.texture = texture;
	}
	public Entity getBlobBit(Entity target) {
		Rectangle bounds = new Rectangle(target.getBounding().x, target.getBounding().y, 0, 0);
		TextureRegion texture;
		bounds.x += MathUtils.random(-8, 8);
		bounds.y += MathUtils.random(-8, 8);
		if (MathUtils.randomBoolean()) {
			bounds.height = bounds.width = 11;
			texture = new TextureRegion(this.texture, 0, 96, 11, 11);
		} else if (MathUtils.randomBoolean()) {
			bounds.height = bounds.width = 9;
			texture = new TextureRegion(this.texture, 11, 96, 9, 9);
		} else if (MathUtils.randomBoolean()) {
			bounds.height = bounds.width = 7;
			texture = new TextureRegion(this.texture, 20, 96, 7, 7);
		} else {
			bounds.height = bounds.width = 5;
			texture = new TextureRegion(this.texture, 27, 96, 5, 5);
		}
		Entity entity = new Entity(bounds);
		entity.setComponent(SimpleSprite.class, new SimpleSprite(texture));
		entity.setComponent(FallingSpeed.class, new FallingSpeed());
		entity.getComponent(FallingSpeed.class).vy = LDUtils.getVelocityY(target) + MathUtils.random(-5, 5);
		entity.setComponent(Weight.class, new Weight(bounds.height / 11f));
		entity.setComponent(RailVelocity.class, new RailVelocity());
		entity.getComponent(RailVelocity.class).increaseVelocity(LDUtils.getVelocityX(target));
		entity.setComponent(BouncinessComponent.class, new BouncinessComponent(1));
		entity.setComponent(PoppedBit.class, new PoppedBit());
		entity.setComponent(Collider.class, new Collider());
		return entity;
	}

	private void stopBit(Entity target, Entity obstacle) {
		Rectangle targetBounds = target.getBounding();
		Rectangle obsBounds = obstacle.getBounding();
		if (obsBounds.contains(targetBounds)) {
			target.removeComponent(FallingSpeed.class);
			target.removeComponent(RailVelocity.class);
		}
	}
	@Override
	public void accept(Message message, Engine engine) {
		if (message instanceof CollisionMessage) {
			CollisionMessage collision = (CollisionMessage) message;
			if (collision.target.hasComponents(PoppedBit.class))
				stopBit(collision.target, collision.obstacle);
		}
	}
}
