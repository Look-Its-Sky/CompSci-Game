package com.judeandsyrus.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World extends Entity
{
    private Texture img;
    private float scale;

    public World(Texture t)
    {
        scale = 1;
        
        this.x = 0;
        this.y = 0;
        this.w = 10000;
        this.h = 10000;

        img = t;
    }

    public Texture returnImg()
    {
        return img;
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
        batch.draw(img, x, y, w, h);
    }

    @Override
    protected void finalize()
    {
        img.dispose();
    }
}
