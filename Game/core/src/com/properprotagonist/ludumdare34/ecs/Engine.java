package com.properprotagonist.ludumdare34.ecs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Spliterator;
import java.util.function.Consumer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.properprotagonist.ludumdare34.ecs.rail.systems.CollisionMessage;

public class Engine {
	public static class DespawnMessage extends Message {
		public final Entity despawned;
		public DespawnMessage(Entity despawned) {
			this.despawned = despawned;
		}
	}
	public static class SpawnMessage extends Message {
		public final Entity spawned;
		public SpawnMessage(Entity spawned) {
			this.spawned = spawned;
		}
	}
	
	public class ComponentEntityList implements Iterable<Entity> {
		private final Class<? extends Component>[] components;
		public ComponentEntityList(Class<? extends Component>... components) {
			this.components = components;
		}
		@Override
		public Iterator<Entity> iterator() {
			return new Iterator<Entity>() {
				Iterator<Entity> baseIterator = entities.iterator();
				Entity nextEntity = null;
				@Override
				public boolean hasNext() {
					while (nextEntity == null) {
						if (baseIterator.hasNext()) {
							Entity candidate = baseIterator.next();
							if (isMine(candidate))
								nextEntity = candidate;
						} else {
							break;
						}
					}
					return nextEntity != null;
				}

				@Override
				public Entity next() {
					Entity current = nextEntity;
					nextEntity = null;
					return current;
				}

				@Override
				public void remove() {
					baseIterator.remove();
				}
			};
		}
		private boolean isMine(Entity entity) {
			return entity.hasComponents(components);
		}
	}

	public LinkedList<Entity> entities = new LinkedList<Entity>();
	public LinkedList<Entity> spawned = new LinkedList<Entity>();
	public LinkedList<Entity> removed = new LinkedList<Entity>();
	private LinkedList<ComponentSystem> systems = new LinkedList<ComponentSystem>();
	private LinkedList<RenderSystem> renderers = new LinkedList<RenderSystem>();
	private LinkedList<ComponentEntityList> systemLists = new LinkedList<ComponentEntityList>();
	private LinkedList<ComponentEntityList> rendererLists = new LinkedList<ComponentEntityList>();
	private HashMap<Class<? extends Message>, LinkedList<MessageListener>> listeners = new HashMap<Class<? extends Message>, LinkedList<MessageListener>>();
	public void addComponentSystem(ComponentSystem system) {
		systems.add(system);
		systemLists.add(new ComponentEntityList(system.dependencies()));
	}
	public void addRenderer(RenderSystem renderer) {
		renderers.add(renderer);
		rendererLists.add(new ComponentEntityList(renderer.dependencies()));
	}
	boolean failed = false;
	float failTimer = 0;
	public void act(float delta) {
		if (failed)
			failTimer -= delta;
		for (int i = 0;i < systems.size();i++) {
			ComponentSystem system = systems.get(i);
			system.act(delta, systemLists.get(i), this);
		}
		clearEntities();
	}
	public void draw(Batch batch, ShapeRenderer shapes) {
		for (int i = 0;i < renderers.size();i++) {
			RenderSystem renderer = renderers.get(i);
			renderer.draw(batch, shapes, rendererLists.get(i));
		}
	}
	public void addEntity(Entity dummy) {
		entities.add(dummy);
	}
	public void spawnEntity(Entity spawn) {
		spawned.add(spawn);
		handleMessage(new SpawnMessage(spawn));
	}
	public void removeEntity(Entity entity) {
		removed.add(entity);
		handleMessage(new DespawnMessage(entity));
	}
	private void clearEntities() {
		for (Entity entity : removed)
			entities.remove(entity);
		for (Entity entity : spawned)
			entities.add(entity);
		removed.clear();
		spawned.clear();
	}
	public void handleMessage(Message message) {
		for (MessageListener listener : getListeners(message.getClass()))
			listener.accept(message, this);
	}
	private LinkedList<MessageListener> getListeners(Class<? extends Message> messageType) {
		if (!listeners.containsKey(messageType))
			listeners.put(messageType, new LinkedList<MessageListener>());
		return listeners.get(messageType);
	}
	public void addListener(Class<? extends Message> messageType, MessageListener listener) {
		getListeners(messageType).add(listener);
	}
	public void triggerFailure(float i) {
		failed = true;
		failTimer = i;
	}
	public boolean failed() {
		return failed && failTimer <= 0;
	}
	public boolean failing() {
		return failed;
	}
}
