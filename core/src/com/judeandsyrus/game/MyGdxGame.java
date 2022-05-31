package com.judeandsyrus.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import sun.font.TrueTypeFont;

public class MyGdxGame extends ApplicationAdapter
{
	//Everything Graphics
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private ExtendViewport viewport;

	//Menu
	private final String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
	private FreeTypeFontGenerator gen;
	private FreeTypeFontGenerator.FreeTypeFontParameter par;
	private BitmapFont font;


	//Camera
	private final int CAMERA_WIDTH = 800;
	private final int CAMERA_HEIGHT = 600;

	private int camX, camY;
	private World world;
	private int gameState;

	//Player Stuff
	private Player p;

	@Override
	public void create()
	{
		gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Macondo-Regular.ttf"));
		par = new FreeTypeFontGenerator.FreeTypeFontParameter();
		par.characters = abc;
		par.size = 20;
		font = gen.generateFont(par);


		//Camera input
		cam = new OrthographicCamera();
		viewport = new ExtendViewport(CAMERA_WIDTH, CAMERA_HEIGHT, cam);
		cam.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);

		//Drawing
		batch = new SpriteBatch();
		p = new Player(100, 100);
		world = new World(new Texture("background.png"));

		//Game stuff
		gameState = 1;
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
		p.checkForInput();

		//Camera Stuff
		if(p.returnX() - p.returnW() > world.returnX() && p.returnX() < world.returnW()) camX = p.returnX();
		if(p.returnY() - p.returnH() > world.returnY() && p.returnY() < world.returnH()) camY = p.returnY();

		cam.position.set(camX, camY, 0);
		cam.update();
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
		gen.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height, true);
		batch.setProjectionMatrix(cam.combined);
	}

}
