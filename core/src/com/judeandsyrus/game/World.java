package com.judeandsyrus.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World extends Entity
{
    private float scale;

    public World(Texture t)
    {
        scale = 10;
        
        this.x = 0;
        this.y = 0;

        sprite = new Sprite(t);
        sprite.scale(scale);
    }

    public Sprite returnImg()
    {
        return sprite;
    }

    @Override 
    public int returnW()
    {
        return (int) (sprite.getWidth() * scale);
    }

    @Override 
    public int returnH()
    {
        return (int) (sprite.getHeight() * scale);
    }

    @Override
    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
    }
}
