package com.properprotagonist.ludumdare34.ecs.rail;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Entity;

public class RailObstacle implements Component {
	private float[] points;
	private boolean startOn;
	public RailObstacle(boolean startOn, float... points) {
		this.points = points;
		this.startOn = startOn;
	}
	public boolean collides(float start, float stop) {
		boolean on = startOn;
		float last = 0;
		for (float p : points) {
			if (on && stop > last && start < p)
				return true;
			on = !on;
			last = p;
		}
		return false;
	}
	public LinkedList<Vector2> lines() {
		LinkedList<Vector2> lines = new LinkedList<Vector2>();
		float last = 0;
		boolean on = startOn;
		for (float p : points) {
			on = !on;
			if (!on)
				lines.add(new Vector2(last, p));
			last = p;
		}
		return lines;
	}
	public float getBottom() {
		if (startOn)
			return 0;
		else
			return points[0];
	}
	public void offset(float y) {
		if (points.length != 2 || startOn)
			return;
		float height = points[1] - points[0];
		points[0] = y;
		points[1] = y + height;
	}
	public boolean collides(RailPosition rPos, Entity entity) {
		float rX = rPos.getPosition();
		if (entity.getBounding().x <= rX && entity.getBounding().x + entity.getBounding().width >= rX)
			return collides(entity.getBounding().y, entity.getBounding().y + entity.getBounding().height);
		else
			return false;
	}
}
