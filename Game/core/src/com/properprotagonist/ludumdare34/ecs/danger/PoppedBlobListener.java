package com.properprotagonist.ludumdare34.ecs.danger;

import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Message;
import com.properprotagonist.ludumdare34.ecs.MessageListener;
import com.properprotagonist.ludumdare34.ecs.blob.Burst;
import com.properprotagonist.ludumdare34.ecs.bounce.BouncinessComponent;
import com.properprotagonist.ludumdare34.ecs.rail.RailVelocity;
import com.properprotagonist.ludumdare34.ecs.rail.systems.CollisionMessage;

public class PoppedBlobListener implements MessageListener {

	@Override
	public void accept(Message message, Engine engine) {
		if (message instanceof CollisionMessage) {
			CollisionMessage collision = (CollisionMessage) message;
			if (collision.obstacle.hasComponents(DangerousObstacle.class))
				popBlob(collision.target, engine);
		}
	}

	private void popBlob(Entity target, Engine engine) {
		if (!engine.failing())
			engine.triggerFailure(1);
		target.getComponent(RailVelocity.class).stop();
		target.removeComponent(Burst.class);
		target.removeComponent(BouncinessComponent.class);
	}

}
