package com.properprotagonist.ludumdare34.ecs;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;

public interface RenderSystem {
	Class<? extends Component>[] dependencies();
	void draw(Batch batch, ShapeRenderer shapes, ComponentEntityList entities);
}
