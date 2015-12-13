package com.properprotagonist.ludumdare34.ecs.danger;

import com.badlogic.gdx.audio.Sound;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Message;
import com.properprotagonist.ludumdare34.ecs.MessageListener;
import com.properprotagonist.ludumdare34.ecs.blob.Burst;
import com.properprotagonist.ludumdare34.ecs.bounce.BouncinessComponent;
import com.properprotagonist.ludumdare34.ecs.powerups.Powerups.Invulnerable;
import com.properprotagonist.ludumdare34.ecs.rail.RailVelocity;
import com.properprotagonist.ludumdare34.ecs.rail.systems.CollisionMessage;

public class PoppedBlobListener implements MessageListener {
	private final Sound popped;
	public PoppedBlobListener(Sound popped) {
		this.popped = popped;
	}
	@Override
	public void accept(Message message, Engine engine) {
		if (message instanceof CollisionMessage) {
			CollisionMessage collision = (CollisionMessage) message;
			if (collision.obstacle.hasComponents(DangerousObstacle.class))
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
			target.getComponent(RailVelocity.class).stop();
			target.removeComponent(Burst.class);
			target.removeComponent(BouncinessComponent.class);
		}
	}

}
