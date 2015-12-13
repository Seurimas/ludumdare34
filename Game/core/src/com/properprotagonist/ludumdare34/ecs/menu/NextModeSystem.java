package com.properprotagonist.ludumdare34.ecs.menu;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.properprotagonist.ludumdare34.LudumDare34;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.ComponentSystem;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class NextModeSystem implements ComponentSystem {
	public static abstract class Mode {
		public final String name;
		public final String description;
		private int highScore = 0;
		public Mode(String name, String description) {
			this.name = name;
			this.description = description;
		}
		public abstract void begin(LudumDare34 game);
		public void setHighScore(int distance) {
			if (distance > highScore)
				highScore = distance;
		}
		public int getHighScore() {
			return highScore;
		}
	}
	private final Entity player;
	private final Mode[] modes;
	private int currentMode = 0;
	public NextModeSystem(Entity player, Mode... modes) {
		this.player = player;
		this.modes = modes;
	}
	@Override
	public Class<? extends Component>[] dependencies() {
		return LDUtils.componentArray();
	}
	private int nextModeIdx() {
		return (currentMode + 1) % modes.length;
	}
	@Override
	public void act(float delta, ComponentEntityList entities, Engine engine) {
		if (player.getBounding().x > LudumDare34.SCREEN_WIDTH) {
			currentMode = nextModeIdx();
			player.getBounding().x = 0;
		}
	}
	public Mode getMode() {
		return modes[currentMode];
	}
	public Mode getNextMode() {
		return modes[nextModeIdx()];
	}
}
