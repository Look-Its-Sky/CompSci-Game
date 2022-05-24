package com.judeandsyrus.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MyGdxGame extends ApplicationAdapter
{
	//Everything Graphics
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private ExtendViewport viewport;

	private final int CAMERA_WIDTH = 800;
	private final int CAMERA_HEIGHT = 600;

	private World world;

	//Game
	private int gameState;
	private int boundOffsetX;
	private int boundOffsetY;

	//Player Stuff
	private Player p;

	private Sprite test;

	@Override
	public void create()
	{
		//Camera input
		cam = new OrthographicCamera();
		viewport = new ExtendViewport(CAMERA_WIDTH, CAMERA_HEIGHT, cam);
		cam.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);

		gameState = 1;

		batch = new SpriteBatch();

		p = new Player(100, 100);
		world = new World(new Texture("background.png"));

		boundOffsetX = 10;
		boundOffsetY = 10;
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		switch(gameState)
		{
			case 0:
				menu_loop();
				break;

			case 1:
				game_loop();
				break;
		}

		batch.end();
	}
	
	@Override
	public void dispose()
	{
		batch.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height, true);
		batch.setProjectionMatrix(cam.combined);
	}


	public void menu_loop()
	{

	}

	public void game_loop()
	{
		batch.setProjectionMatrix(cam.combined);

		//If i have to explain this part stop reading and kindly leave :)
		world.render(batch);
		p.render(batch);


		//Camera Stuff
		cam.position.set(p.returnX(), p.returnY(), 0);
		cam.update();
	}
}
