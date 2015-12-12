package com.properprotagonist.ludumdare34.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;
import com.properprotagonist.ludumdare34.ecs.gravity.Weight;
import com.properprotagonist.ludumdare34.ecs.rail.RailObstacle;
import com.properprotagonist.ludumdare34.ecs.rail.RailPosition;
import com.properprotagonist.ludumdare34.utils.GdxUtils;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class DebugRailObstacleRenderer implements RenderSystem {

	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(RailPosition.class,
			RailObstacle.class);
	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}

	@Override
	public void draw(Batch batch, ShapeRenderer shapes, ComponentEntityList entities) {
		batch.end();
		shapes.begin(ShapeType.Filled);
		shapes.setColor(Color.RED);
		for (Entity entity : entities) {
			RailPosition rPos = entity.getComponent(RailPosition.class);
			RailObstacle rObs = entity.getComponent(RailObstacle.class);
			for (Vector2 lines : rObs.lines()) {
				shapes.rect(rPos.getPosition(), lines.x, 3, lines.y - lines.x);
			}
		}
		shapes.end();
		batch.begin();
	}

}
