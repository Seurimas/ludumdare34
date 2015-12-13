package com.properprotagonist.ludumdare34.ecs;

public class DespawnMessage extends Message {
	public final Entity despawned;
	public DespawnMessage(Entity despawned) {
		this.despawned = despawned;
	}
}
