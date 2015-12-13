package com.properprotagonist.ludumdare34.ecs.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Message;
import com.properprotagonist.ludumdare34.ecs.MessageListener;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;

public class DistanceRenderer implements RenderSystem, MessageListener {

	public DistanceRenderer(Entity dummy) {
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Batch batch, ShapeRenderer shapes,
			ComponentEntityList componentEntityList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void accept(Message message, Engine engine) {
		
	}

}
