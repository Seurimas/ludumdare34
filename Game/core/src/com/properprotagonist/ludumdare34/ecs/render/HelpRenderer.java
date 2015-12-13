package com.properprotagonist.ludumdare34.ecs.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.properprotagonist.ludumdare34.LudumDare34;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;

public class HelpRenderer implements RenderSystem {
	Entity player;
	Batch uiBatch;
//	Texture uiTexture;
	TextureRegion spaceHelp;
	TextureRegion enterHelp;
	public HelpRenderer(Entity player, Batch uiBatch, Texture uiTexture) {
		this.player = player;
		this.uiBatch = uiBatch;
//		this.uiTexture = uiTexture;
		spaceHelp = new TextureRegion(uiTexture, 0, 0, 128, 32);
		enterHelp = new TextureRegion(uiTexture, 0, 32, 128, 32);
	}

	@Override
	public Class<? extends Component>[] dependencies() {
		return new Class[0];
	}

	@Override
	public void draw(Batch batch, ShapeRenderer shapes,
			ComponentEntityList entities) {
		batch.end();
		uiBatch.begin();
		uiBatch.draw(spaceHelp, 0, LudumDare34.SCREEN_HEIGHT / 2 - 32);
		uiBatch.draw(enterHelp, 0, LudumDare34.SCREEN_HEIGHT / 2);
		uiBatch.end();
		batch.begin();
	}

}
