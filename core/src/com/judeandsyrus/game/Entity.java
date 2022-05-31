package com.judeandsyrus.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public abstract class Entity
{
    protected Anim currentAnim;
    protected Sprite sprite;
    protected float time;
    protected float scale = 3;
    protected int x,y, w, h;
    protected int atk, spd, hlth;

    public int returnX()
    {
        return x;
    }

    public int returnY()
    {
        return y;
    }

    public int returnW()
    {
        return w;
    }

    public int returnH()
    {
        return h;
    }

    public void attack(Entity e)
    {
        e.hurt(atk);
    }

    public void hurt(int i)
    {
        hlth -= i;
    }

    public Sprite sprite()
    {
        return sprite;
    }

    public void move(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch)
    {
        sprite = currentAnim.currentSprite();
        sprite.setPosition((float) x, (float) y);
        sprite.setScale(scale);
        sprite.draw(batch);
    }
}
