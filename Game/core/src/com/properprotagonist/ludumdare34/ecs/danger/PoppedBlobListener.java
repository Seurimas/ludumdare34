package com.properprotagonist.ludumdare34.ecs.danger;

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

public class PoppedBlobListener implements MessageListener {
	private final Sound popped;
	private final BlobBits bits;
	public PoppedBlobListener(Sound popped, BlobBits blobBits) {
		this.popped = popped;
		this.bits = blobBits;
	}
	@Override
	public void accept(Message message, Engine engine) {
		if (message instanceof CollisionMessage) {
			CollisionMessage collision = (CollisionMessage) message;
			if (collision.obstacle.hasComponents(DangerousObstacle.class) && collision.target.hasComponents(BlobComponent.class))
				popBlob(collision.target, engine);
		}
	}
	private void popBlob(Entity target, Engine engine) {
		if (target.hasComponents(Invulnerable.class)) {
			return;
		}
		if (!engine.failing()) {
			popped.play(0.75f);
			engine.triggerFailure(1);
			for (int i = 0;i < 5;i++) {
				engine.spawnEntity(bits.getBlobBit(target));
			}
			target.getComponent(RailVelocity.class).stop();
			target.removeComponent(Burst.class);
			target.removeComponent(BouncinessComponent.class);
			target.removeComponent(BlobSprite.class);
		}
	}
	

}
