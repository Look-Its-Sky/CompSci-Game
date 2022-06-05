package com.judeandsyrus.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public class Player extends Entity
{

    /*
    Wow they make animation hard asf to implement

    im making my own
     */

    private String state;
    private String prevState;
    private Anim runAnim, deathAnim, idle1Anim, idle2Anim,attack1Anim, attack2Anim, attack3Anim, attack4Anim;
    private boolean superSpeedToggle = false;
    private int superSpd;
    private int regSpd;
    private Random rand;
    private int[] origin;

    private boolean isAtkUp, isAtkDown, isAtkLeft, isAtkRight;

    public Player(int x, int y)
    {
        //Basic pos and stats
        this.x = x;
        this.y = y;

        hlth = 100;
        regSpd = 6;
        superSpd = 20;
        spd = regSpd;

        origin = new int[2];
        origin[0] = 0;
        origin[1] = 0;

        rand = new Random();

        state = "idle";
        runAnim = new Anim(10, "player_run/spritesheet.txt", 6, true);
        idle1Anim = new Anim(10, "player_idle1/spritesheet.txt", 4, true);
        idle2Anim = new Anim(10, "player_idle2/spritesheet.txt", 4, true);
        attack1Anim = new Anim(10, "player_attack1/spritesheet.txt", 5, false);
        attack2Anim = new Anim(10, "player_attack2/spritesheet.txt", 6, false);
        attack3Anim = new Anim(10, "player_attack3/spritesheet.txt", 4, false);
        attack4Anim = new Anim(10, "player_attack4/spritesheet.txt", 6, false);
        deathAnim = new Anim(10, "player_death/spritesheet.txt", 7, false);

        currentAnim = runAnim;
        sprite = runAnim.currentSprite();
        sprite.setScale(scale);

        isAtkUp = false;
        isAtkDown = false;
        isAtkLeft = false;
        isAtkRight = false;
    }

    public void checkForInput() {
        prevState = state;
        int buttonsDown = 0;

        //Check for input
        if (Gdx.input.isKeyPressed(ctrls.UP.key) && !Gdx.input.isKeyPressed(ctrls.DOWN.key))
        {
            y += spd;
            buttonsDown++;
            state = "run";
        }

        if (Gdx.input.isKeyPressed(ctrls.DOWN.key) && !Gdx.input.isKeyPressed(ctrls.UP.key))
        {
            y -= spd;
            buttonsDown++;
            state = "run";
        }

        if (Gdx.input.isKeyPressed(ctrls.LEFT.key) && !Gdx.input.isKeyPressed(ctrls.RIGHT.key))
        {
            x -= spd;
            buttonsDown++;
            flip = true;
            state = "run";
        }

        if (Gdx.input.isKeyPressed(ctrls.RIGHT.key) && !Gdx.input.isKeyPressed(ctrls.LEFT.key))
        {
            x += spd;
            buttonsDown++;
            flip = false;
            state = "run";
        }

        if(Gdx.input.isKeyPressed(ctrls.ATK_UP.key) && !Gdx.input.isKeyPressed(ctrls.ATK_DOWN.key))
        {
            state = "attack";
            isAtkUp = true;
            buttonsDown++;
        }

        if(Gdx.input.isKeyPressed(ctrls.ATK_DOWN.key) && !Gdx.input.isKeyPressed(ctrls.ATK_UP.key))
        {
            state = "attack";
            isAtkDown = true;
            buttonsDown++;
        }

        if(Gdx.input.isKeyPressed(ctrls.ATK_LEFT.key) && !Gdx.input.isKeyPressed(ctrls.ATK_RIGHT.key))
        {
            state = "attack";
            isAtkLeft = true;
            buttonsDown++;
        }

        if(Gdx.input.isKeyPressed(ctrls.ATK_RIGHT.key) && !Gdx.input.isKeyPressed(ctrls.ATK_LEFT.key))
        {
            state = "attack";
            isAtkRight = true;
            buttonsDown++;
        }

        if(buttonsDown == 0) state = "idle";

        if(!currentAnim.interruptable() && !currentAnim.done()) state = prevState;

        switch(state)
        {
            case "idle":
                if(rand.nextBoolean() || prevState == state) currentAnim = idle1Anim;
                else currentAnim = idle2Anim;
                break;

            case "run":
                currentAnim = runAnim;
                break;

            case "attack":
                currentAnim = attack1Anim;
                break;

            default:
                System.err.println("Error in Rendering Player... Exiting");
                System.exit(1);
        }

        if(prevState != state && currentAnim.interruptable()) currentAnim.reset();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            if(superSpeedToggle) spd = 6;
            else spd = superSpd;

            superSpeedToggle = !superSpeedToggle;
        }

        sprite.setPosition((float) x, (float) y);
    }

    private void attack()
    {

    }

    public int[] getDistanceFromOrigin()
    {
        int[] temp = new int[2];
        temp[0] = origin[0] - this.x;
        temp[1] = origin[1] - this.y;

        return temp;
    }

    public void updateOrigin(int[] o)
    {
        origin = o;
    }


    //Controls
    public enum ctrls
    {
        UP(Input.Keys.W),
        DOWN(Input.Keys.S),
        LEFT(Input.Keys.A),
        RIGHT(Input.Keys.D),
        ATK_UP(Input.Keys.I),
        ATK_DOWN(Input.Keys.K),
        ATK_RIGHT(Input.Keys.L),
        ATK_LEFT(Input.Keys.J);

        public int key;

        private ctrls(int key)
        {
            this.key = key;
        }
    }

}
