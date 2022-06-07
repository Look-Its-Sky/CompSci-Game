package com.judeandsyrus.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class World extends Entity
{
    private Texture img;
    private float scale;
    private ArrayList<Enemy> enemies;
    private Random rand;
    private int spawnNum;
    private int timeForNextSpawn;
    private int wave;

    public World(Texture t)
    {
        scale = 1;
        img = t;

        this.x = 0;
        this.y = 0;
        this.w = 2000;
        this.h = 2000;

        enemies = new ArrayList<Enemy>();
        rand = new Random();

        spawnNum = 3;
        wave = 1;
    }

    public Texture returnImg()
    {
        return img;
    }

    private void spawnEnemies(int camX, int camY, int CAMERA_WIDTH, int CAMERA_HEIGHT)
    {
        boolean r, r2;
        int eX, eY;

        for(int i = 0; i < spawnNum; i++)
        {
            r = rand.nextBoolean();
            r2 = rand.nextBoolean();

            if(r) eX = camX - CAMERA_WIDTH;
            else eX = camX + CAMERA_WIDTH;

            if(r2) eY = camY - CAMERA_HEIGHT;
            else eY = camY + CAMERA_HEIGHT;

            enemies.add(i, new Enemy(eX,eY));
        }
    }

    public void update(Player p, int CAMERA_WIDTH, int CAMERA_HEIGHT)
    {
        for(int i = 0; i < enemies.size(); i++)
        {
            if(enemies.get(i) != null && enemies.get(i).isDead()) enemies.remove(i);
        }

        if(enemies.size() < wave)
        {
            spawnEnemies(p.returnX(), p.returnY(), CAMERA_WIDTH, CAMERA_HEIGHT);
        }
    }

    public ArrayList<Enemy> enemies()
    {
        return enemies;
    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(img, 0, 0, w, h);

        for(int i = 0; i < enemies.size(); i++)
        {
            enemies.get(i).render(batch);
        }
    }

    @Override
    protected void finalize()
    {
        img.dispose();
    }
}
