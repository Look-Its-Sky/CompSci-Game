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
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;
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
import io.socket.client.IO;
import io.socket.client.Socket;

public class MyGdxGame extends ApplicationAdapter
{
	//Everything Graphics
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private ExtendViewport viewport;
	private ShapeRenderer sr;
	//Health Bar Shenanigans
	int i=0;
	Texture texture,texture2;
	//Server-sided stuff
	private Socket  socket;
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
	private Player p2;

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

		//Server Shenanigans
		connectSocket();
		configSocketEvents();
	}
	public void connectSocket() { //Server shenanigan known as connecting
		try {
			socket = IO.socket("http://141.148.142.190:6969");
			socket.connect();
			System.out.println("Server connected");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void configSocketEvents() { //Server configurer
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@java.lang.Override
			public void call(java.lang.Object... args) {
				Gdx.app.log("SocketIO", "Connected");
			}
		}).on("socketID", new Emitter.Listener() {
			@java.lang.Override
			public void call(java.lang.Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					p2 = new Player(250, 250);
					String id = data.getString("id");
					Gdx.app.log("SocketIO", "My ID: " + id);
				}catch(JSONException e) {
					Gdx.app.log("SocketIO", "Error getting ID");
				}
			}
		}).on("newPlayer", new Emitter.Listener() {
			@java.lang.Override
			public void call(java.lang.Object... args) {
				JSONObject data = (JSONObject) args[0];
				System.out.println("Hello there");
				try {
					String id = data.getString("id");
					Gdx.app.log("SocketIO", "New Player Connected ID: " + id);
				}catch(JSONException e) {
					Gdx.app.log("SocketIO", "Error getting ID");
				}
			}
		});
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
		batch.begin();
		batch.draw(texture2,p.returnX()-30,p.returnY()-50,100,20);
		batch.draw(texture,p.returnX()-30,p.returnY()-50,p.returnhlth(),20);
		batch.end();
		if(p2 != null){
			p.render(batch);
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
