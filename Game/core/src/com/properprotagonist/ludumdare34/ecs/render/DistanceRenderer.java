package com.properprotagonist.ludumdare34.ecs.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.properprotagonist.ludumdare34.LudumDare34;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.Message;
import com.properprotagonist.ludumdare34.ecs.MessageListener;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class DistanceRenderer implements RenderSystem {
	private static final Class<? extends Component>[] dependencies = LDUtils.componentArray(); 
	private final Entity player;
	private final BitmapFont font;
	private final Batch uiBatch;
	public DistanceRenderer(Entity dummy, BitmapFont font, Batch uiBatch) {
		this.player = dummy;
		this.font = font;
		this.uiBatch = uiBatch;
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return dependencies;
	}

	@Override
	public void draw(Batch batch, ShapeRenderer shapes,
			ComponentEntityList componentEntityList) {
		batch.end();
		uiBatch.begin();
		int distance = (int) (player.getBounding().x / 200);
		font.draw(uiBatch, String.format("Distance: %d", distance), 0, LudumDare34.SCREEN_HEIGHT);
		uiBatch.end();
		batch.begin();
	}

}
