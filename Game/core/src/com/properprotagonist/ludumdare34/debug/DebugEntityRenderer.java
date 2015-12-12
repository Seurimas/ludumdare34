package com.properprotagonist.ludumdare34.debug;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.danger.DangerousObstacle;
import com.properprotagonist.ludumdare34.ecs.gravity.Weight;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;
import com.properprotagonist.ludumdare34.utils.GdxUtils;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class DebugEntityRenderer implements RenderSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray();
	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}

	@Override
	public void draw(Batch batch, ShapeRenderer shapes, ComponentEntityList entities) {
		batch.end();
		shapes.begin(ShapeType.Line);
		for (Entity entity : entities) {
			Rectangle bounding = entity.getBounding();
			if (entity.hasComponents(DangerousObstacle.class))
				shapes.setColor(Color.RED);
			else
				shapes.setColor(Color.WHITE);
			shapes.rect(bounding.x, bounding.y, bounding.width, bounding.height);
		}
		shapes.end();
		batch.begin();
	}


}
