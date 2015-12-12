package com.properprotagonist.ludumdare34.ecs;

import java.util.HashMap;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.properprotagonist.ludumdare34.ecs.gravity.BounceComponent;

public class Entity {
	private HashMap<Class<? extends Component>, Component> components = new HashMap<Class<? extends Component>, Component>();
	public <C extends Component> C getComponent(Class<C> clazz) {
		return (C) components.get(clazz);
	}
	public <C extends Component> void setComponent(Class<C> clazz, C component) {
		components.put(clazz, component);
	}
	public boolean hasComponents(Class<? extends Component>... checkFor) {
		for (Class<? extends Component> checked : checkFor) {
			if (!components.containsKey(checked))
				return false;
		}
		return true;
	}
	private Rectangle bounding;
	public Entity(Rectangle bounding) {
		this.bounding = bounding;
	}
	Vector2 myVector = new Vector2(0, 0);
	public Vector2 getPosition() {
		return bounding.getPosition(myVector);
	}
	public Rectangle getBounding() {
		return bounding;
	}
	public void removeComponent(Class<? extends Component> checkFor) {
		components.remove(checkFor);
	}
}
