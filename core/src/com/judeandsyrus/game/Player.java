package com.judeandsyrus.game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public class Player extends Entity
{
    private float scale;

    /*
    Wow they make animation hard asf to implement

    im making my own
     */

    private String state;
    private Anim runAnim;

    public Player(int x, int y)
    {
        //Basic pos and stats
        this.x = x;
        this.y = y;

        hlth = 100;
        spd = 20;

        scale = 5;

        state = "run";
        runAnim = new Anim(10, "player_run/spritesheet.txt", 3);

        currentAnim = runAnim;

        sprite = runAnim.currentSprite();
        sprite.scale(scale);
    }

    public void checkForInput()
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

        sprite.setPosition((float) x, (float) y);
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

    @Override
    protected void finalize() throws Throwable
    {

    }
}
