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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import sun.font.TrueTypeFont;
import java.util.ArrayList;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter
{
	//Everything Graphics
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private ExtendViewport viewport;
	private ShapeRenderer sr;

	//Menu
	private final String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
	private FreeTypeFontGenerator gen;
	private FreeTypeFontGenerator.FreeTypeFontParameter par;
	private BitmapFont font;
	private int selectedItem;


	//Camera
	private final int CAMERA_WIDTH = 800;
	private final int CAMERA_HEIGHT = 600;
	private final int paddingX = 0; //I hate this with a passion - 500
	private final int paddingY = 0; //400

	private int camX, camY;
	private World world;
	private int gameState;

	//Player Stuff
	private Player p;

	@Override
	public void create()
	{
		//Font
		gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Macondo-Regular.ttf"));
		par = new FreeTypeFontGenerator.FreeTypeFontParameter();
		par.characters = abc;
		par.size = 20;
		font = gen.generateFont(par);

		//Menu
		selectedItem = 0;

		//Camera input
		cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		cam.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
		viewport = new ExtendViewport(CAMERA_WIDTH, CAMERA_HEIGHT, cam);

		//Drawing
		batch = new SpriteBatch();
		p = new Player(250, 250);
		world = new World(new Texture("background.png"));
		sr = new ShapeRenderer();

		//Game stuff
		gameState = 1;
	}

	public void menu_loop()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) selectedItem--;
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) selectedItem++;

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && selectedItem == 0) gameState++; //Start game
	}

	public void game_loop()
	{
		batch.setProjectionMatrix(cam.combined);
		batch.begin();

		world.render(batch);

		p.render(batch);
		p.checkForInput();


		batch.end();

		//Camera Stuff
		if(p.returnX() - p.returnW() > world.returnX() + paddingX && p.returnX() < world.returnX() + world.returnW() - paddingX) camX = p.returnX();
		if(p.returnY() - p.returnH() > world.returnY() + paddingY && p.returnY() < world.returnY() + world.returnH() - paddingY) camY = p.returnY();

		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && false) System.out.println("Player Coords: (" + Gdx.input.getX() + "," + Gdx.input.getY() + ")\n" + "World Coords: (" + world.returnX() +"," + world.returnY()  + ")");

		cam.position.set(camX, camY, 0);
		cam.update();
	}

	@Override
	public void render()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		switch(gameState)
		{
			case 0:
				menu_loop();
				break;

			case 1:
				//2, 232, 21
				Gdx.gl.glClearColor(0/255f, 205/255f, 30/255f, 1); //Lol intentional i swear
				game_loop();
				break;
		}
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
		batch.setProjectionMatrix(cam.combined);
		viewport.update(width, height);
	}
}
