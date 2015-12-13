package com.properprotagonist.ludumdare34.ecs.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.properprotagonist.ludumdare34.LudumDare34;
import com.properprotagonist.ludumdare34.ecs.Component;
import com.properprotagonist.ludumdare34.ecs.Engine.ComponentEntityList;
import com.properprotagonist.ludumdare34.ecs.bounce.FloorSystem;
import com.properprotagonist.ludumdare34.ecs.RenderSystem;
import com.properprotagonist.ludumdare34.utils.LDUtils;

public class ModeRenderer implements RenderSystem {
	private final NextModeSystem modeSystem;
	private final BitmapFont font;
	public ModeRenderer(NextModeSystem modeSystem, BitmapFont font) {
		this.modeSystem = modeSystem;
		this.font = font;
	}
	@Override
	public Class<? extends Component>[] dependencies() {
		return LDUtils.componentArray();
	}
	@Override
	public void draw(Batch batch, ShapeRenderer shapes,
			ComponentEntityList entities) {
		String currentMode = "Bounce to begin: " + modeSystem.getMode().name;
		String modeDescription = modeSystem.getMode().description;
		String nextMode = "Travel to: " + modeSystem.getNextMode().name;
		font.setColor(Color.WHITE);
		float yOff = FloorSystem.CEILING - 32;
		yOff -= centerText(batch, currentMode, LudumDare34.SCREEN_WIDTH / 2, yOff);
		String highScore = "High score: " + modeSystem.getMode().getHighScore();
		yOff -= centerText(batch, modeDescription, LudumDare34.SCREEN_WIDTH / 2, yOff);
		yOff -= font.getLineHeight();
		yOff -= centerText(batch, highScore, LudumDare34.SCREEN_WIDTH / 2, yOff);
		GlyphLayout layout2 = new GlyphLayout(font, nextMode);
		font.draw(batch, nextMode, LudumDare34.SCREEN_WIDTH - layout2.width, (FloorSystem.CEILING + FloorSystem.FLOOR) / 2 - layout2.height / 2);
	}
	private float centerText(Batch batch, String text, float xCenter, float yCenter) {
		GlyphLayout layout = new GlyphLayout(font, text);
		font.draw(batch, text, xCenter - layout.width / 2, yCenter - layout.height);
		return layout.height;
	}
}
