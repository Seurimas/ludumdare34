package com.properprotagonist.ludumdare34.ecs.bounce;

import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Message;

public class BounceMessage extends Message {
	public final Entity bouncer;
	public final float speed;
	public BounceMessage(Entity bouncer, float speed) {
		this.bouncer = bouncer;
		this.speed = speed;
	}
}
