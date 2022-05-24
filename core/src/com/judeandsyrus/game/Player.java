package com.judeandsyrus.game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Player extends Entity
{
    private TextureAtlas ta;
    private float scale;
    private Anim idleAnim;

    public Player(int x, int y)
    {
        this.x = x;
        this.y = y;

        hlth = 100;
        spd = 5;

        scale = 5;
        time = 0f;

        ta = new TextureAtlas("spritesheet.txt");

        sprite = ta.createSprite("player_idle_f0");
        sprite.scale(scale);

        idleAnim = new Anim(25);
        for(int i = -1; i <= 2; i++)
        {
            idleAnim.add(ta.createSprite("player_idle_f" + i));
        }

        currentAnim = idleAnim;
    }

    protected void checkForInput()
    {
        //Check for input
        if(Gdx.input.isKeyPressed(ctrls.UP.key))
        {
            y += spd;
        }

        if(Gdx.input.isKeyPressed(ctrls.DOWN.key))
        {
            y -= spd;
        }

        if(Gdx.input.isKeyPressed(ctrls.LEFT.key))
        {
            x -= spd;
        }

        if(Gdx.input.isKeyPressed(ctrls.RIGHT.key))
        {
            x += spd;
        }
    }

    //Controls
    public enum ctrls
    {
        UP(Input.Keys.W),
        DOWN(Input.Keys.S),
        LEFT(Input.Keys.A),
        RIGHT(Input.Keys.D);

        public int key;

        private ctrls(int key)
        {
            this.key = key;
        }
    }

    //Reverse Bob the builder... bob the destroyer😈.. get it cause it's a- nvm
    @Override
    protected void finalize() throws Throwable
    {
    try
    {
        ta.dispose();
    }

    catch(Exception e)
    {
        e.printStackTrace();
    }

}
}
