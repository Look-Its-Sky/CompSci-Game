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
    }

    public Texture returnImg()
    {
        return img;
    }

    public void spawnEnemies(int camX, int camY, int CAMERA_WIDTH, int CAMERA_HEIGHT)
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

    /*
    @Deprecated
    @Override
    public int returnW()
    {
        return (int) (sprite.getWidth() * scale);
    }

    @Deprecated
    @Override
    public int returnH()
    {
        return (int) (sprite.getHeight() * scale);
    }
     */

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(img, 0, 0, w, h);
    }

    @Override
    protected void finalize()
    {
        img.dispose();
    }
}
