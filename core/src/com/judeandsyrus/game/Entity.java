package com.judeandsyrus.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

import javax.swing.plaf.TextUI;

public abstract class Entity
{
    protected Anim currentAnim;
    protected Sprite sprite;
    protected float time;
    protected float scale;
    protected int x,y, w, h;
    protected int atk, spd, hlth;

    public Entity()
    {
        currentAnim = null;
        sprite = null;
    }

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
        if(currentAnim != null && sprite != null)
        {
            try
            {
                sprite = new Sprite(currentAnim.returnCurrentImg());
                System.gc(); //AHHHHHHHHH JAVA PROBLEMS
            }

            catch(Exception e)
            {
                e.printStackTrace();
                System.err.println("Uh issue rendering something abort abort");
                System.exit(1);
            }

            sprite.draw(batch);
        }
    }
}
