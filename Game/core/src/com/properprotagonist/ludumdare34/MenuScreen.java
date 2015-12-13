package com.properprotagonist.ludumdare34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.properprotagonist.ludumdare34.ecs.Engine;
import com.properprotagonist.ludumdare34.ecs.Entity;
import com.properprotagonist.ludumdare34.ecs.blob.BlobComponent;
import com.properprotagonist.ludumdare34.ecs.bounce.FloorSystem;
import com.properprotagonist.ludumdare34.ecs.danger.DangerousObstacle;
import com.properprotagonist.ludumdare34.ecs.menu.ModeRenderer;
import com.properprotagonist.ludumdare34.ecs.menu.NextModeSystem;
import com.properprotagonist.ludumdare34.ecs.menu.TargetObstacle;
import com.properprotagonist.ludumdare34.ecs.menu.NextModeSystem.Mode;
import com.properprotagonist.ludumdare34.ecs.rail.Obstacle;
import com.properprotagonist.ludumdare34.ecs.render.Extended9PatchRenderer.Extended9Patch;
import com.properprotagonist.ludumdare34.ecs.render.CenteredTextSystem;
import com.properprotagonist.ludumdare34.ecs.render.HelpRenderer;

public class MenuScreen implements Screen {
	LudumDare34 game;
	Engine engine;
	OrthographicCamera camera;
	Entity player;
	Matrix4 stored = new Matrix4();
	NextModeSystem modes;
	static Mode normalMode = getNormalMode();
	static Mode classicMode = getClassicMode();
	static Mode hardMode = getHardMode();
	public MenuScreen(LudumDare34 game) {
		this.game = game;
		engine = new Engine();
		camera = new OrthographicCamera(800, 600);
		EngineSetup.addCriticalSystems(engine);
		player = EngineSetup.createPlayer(engine, false);
		EngineSetup.setupRendering(engine, player, camera, game.uiBatch, false);
		modes = new NextModeSystem(player, normalMode, classicMode, hardMode);
		engine.addComponentSystem(modes);
		engine.addRenderer(new ModeRenderer(modes, EngineSetup.getFont()));
		engine.addEntity(getStartButton());
		engine.addRenderer(new HelpRenderer(player, game.uiBatch, EngineSetup.getUi()));
	}
	public Entity getStartButton() {
		Entity obs = new Entity(new Rectangle(0, FloorSystem.CEILING - 32, LudumDare34.SCREEN_WIDTH, 30));
		obs.setComponent(Obstacle.class, new Obstacle());
		obs.setComponent(DangerousObstacle.class, new DangerousObstacle());
		obs.setComponent(TargetObstacle.class, new TargetObstacle() {
			@Override
			public void activate(Engine engine, Entity activater, Entity target) {
				if (activater.hasComponents(BlobComponent.class))
					engine.removeEntity(target);
			}
		});
		CenteredTextSystem.speak(obs, "Start", Color.GREEN);
		Extended9Patch patch = EngineSetup.getTarget9Patch();
		patch.setColor(Color.GREEN);
		obs.setComponent(Extended9Patch.class, patch);
		return obs;
	}
	private static Mode getNormalMode() {
		return new Mode("Normal Mode", "Grow and bounce to clear obstacles. Don't get popped!") {
			@Override
			public void begin(LudumDare34 game) {
				game.start(1, true, this);
			}
		};
	}
	private static Mode getClassicMode() {
		return new Mode("Classic Mode", "All the bouncy action, no pesky powerups.") {
			@Override
			public void begin(LudumDare34 game) {
				game.start(1, false, this);
			}
		};
	}
	private static Mode getHardMode() {
		return new Mode("Turbo Mode", "Speed through 50% faster than normal!") {
			@Override
			public void begin(LudumDare34 game) {
				game.start(1.5f, true, this);
			}
		};
	}
	@Override
	public void show() {
		stored.set(game.batch.getProjectionMatrix());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		engine.act(delta);
		game.batch.begin();
		engine.draw(game.batch, game.shapes);
		game.batch.end();
		if (engine.failed())
			modes.getMode().begin(game);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		game.batch.setProjectionMatrix(stored);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
