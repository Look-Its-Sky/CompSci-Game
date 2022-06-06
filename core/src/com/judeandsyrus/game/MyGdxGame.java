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
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter
{
	//Everything Graphics
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private ExtendViewport viewport;
	private ShapeRenderer sr;

	int i=0;
	Texture texture,texture2;
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
		//Test
		batch = new SpriteBatch();

		initTestObjects();
		// Font
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
	private void initTestObjects() {

		int width =1 ;
		int height = 1;
		Pixmap pixmap = createProceduralPixmap(width, height,0,1,0);
		Pixmap pixmap2 = createProceduralPixmap(width, height,1,0,0);

		texture = new Texture(pixmap);
		texture2 = new Texture(pixmap2);
	}



	private Pixmap createProceduralPixmap (int width, int height,int r,int g,int b) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);

		pixmap.setColor(r, g, b, 1);
		pixmap.fill();

		return pixmap;
	}
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();


		batch.draw(texture2,100,100,300,20);
		batch.draw(texture,100,100,i,20);
		if(i>300)
		{
			i=0;
		}
		i++;

		batch.end();
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
