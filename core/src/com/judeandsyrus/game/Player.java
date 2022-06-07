package com.judeandsyrus.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity
{

    /*
    Wow they make animation hard asf to implement

    im making my own
     */
    //why
    private String state;
    private String prevState;
    private Anim runAnim, deathAnim, idle1Anim, idle2Anim,attack1Anim, attack2Anim, attack3Anim, attack4Anim;
    private boolean superSpeedToggle = false;
    private int superSpd;
    private int regSpd;
    private Random rand;
    private int[] origin;
    private boolean isAtkUp, isAtkDown, isAtkLeft, isAtkRight;

    Vector2 previousPosition = new Vector2(getX(),getY());
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

        //Animations and stuff
        state = "idle";
        runAnim = new Anim(10, "player_run/spritesheet.txt", 6, true);

        idle1Anim = new Anim(10, "player_idle1/spritesheet.txt", 4, true);
        idle2Anim = new Anim(10, "player_idle2/spritesheet.txt", 4, true);

        attack1Anim = new Anim(2, "player_attack1/spritesheet.txt", 5, false);
        attack2Anim = new Anim(5, "player_attack2/spritesheet.txt", 6, false);
        attack3Anim = new Anim(3, "player_attack3/spritesheet.txt", 4, false);
        attack4Anim = new Anim(2, "player_attack4/spritesheet.txt", 6, false);

        deathAnim = new Anim(10, "player_death/spritesheet.txt", 7, false);

        currentAnim = runAnim;
        sprite = runAnim.currentSprite();
        sprite.setScale(scale);

        isAtkUp = false;
        isAtkDown = false;
        isAtkLeft = false;
        isAtkRight = false;
    }

    public void checkForInput()
    {
        prevState = state;
        int buttonsDown = 0;

        if(currentAnim.done() || state == "run" || state == "idle" || state == "attack1")
        {
            if(state == "attack1") spd = spd/2;
            else spd = regSpd;

            //Check for input
            if (Gdx.input.isKeyPressed(ctrls.UP.key) && !Gdx.input.isKeyPressed(ctrls.DOWN.key))
            {
                y += spd;
                buttonsDown++;
                if(!state.contains("attack") || currentAnim.done()) state = "run";
            }

            if (Gdx.input.isKeyPressed(ctrls.DOWN.key) && !Gdx.input.isKeyPressed(ctrls.UP.key))
            {
                y -= spd;
                buttonsDown++;
                if(!state.contains("attack") || currentAnim.done()) state = "run";
            }

            if (Gdx.input.isKeyPressed(ctrls.LEFT.key) && !Gdx.input.isKeyPressed(ctrls.RIGHT.key))
            {
                x -= spd;
                buttonsDown++;
                flip = true;
                if(!state.contains("attack") || currentAnim.done()) state = "run";
            }

            if (Gdx.input.isKeyPressed(ctrls.RIGHT.key) && !Gdx.input.isKeyPressed(ctrls.LEFT.key))
            {
                x += spd;
                buttonsDown++;
                flip = false;
                if(!state.contains("attack") || currentAnim.done()) state = "run";
            }

            if(!state.contains("attack"))
            {
                if (Gdx.input.isKeyJustPressed(ctrls.ATK1.key) && !Gdx.input.isKeyPressed(ctrls.ATK2.key) && !Gdx.input.isKeyPressed(ctrls.ATK3.key) && !Gdx.input.isKeyPressed(ctrls.ATK4.key))
                {
                    state = "attack1";
                    buttonsDown++;
                }

                if (Gdx.input.isKeyJustPressed(ctrls.ATK2.key) && !Gdx.input.isKeyPressed(ctrls.ATK1.key) && !Gdx.input.isKeyPressed(ctrls.ATK3.key) && !Gdx.input.isKeyPressed(ctrls.ATK4.key))
                {
                    state = "attack2";
                    buttonsDown++;
                }

                if (Gdx.input.isKeyJustPressed(ctrls.ATK3.key) && !Gdx.input.isKeyPressed(ctrls.ATK1.key) && !Gdx.input.isKeyPressed(ctrls.ATK2.key) && !Gdx.input.isKeyPressed(ctrls.ATK4.key))
                {
                    state = "attack3";
                    buttonsDown++;
                }

                if (Gdx.input.isKeyJustPressed(ctrls.ATK4.key) && !Gdx.input.isKeyPressed(ctrls.ATK1.key) && !Gdx.input.isKeyPressed(ctrls.ATK2.key) && !Gdx.input.isKeyPressed(ctrls.ATK3.key))
                {
                    state = "attack4";
                    buttonsDown++;
                }
            }

            if (buttonsDown == 0 && currentAnim.done()) state = "idle";
        }

        switch(state)
        {
            case "idle":
                if(rand.nextBoolean() || prevState == state) currentAnim = idle1Anim;
                else currentAnim = idle2Anim;
                break;

            case "run":
                currentAnim = runAnim;
                break;

            case "attack1":
                currentAnim = attack1Anim;
                break;

            case "attack2":
                currentAnim = attack2Anim;
                break;

            case "attack3":
                currentAnim = attack3Anim;
                break;

            case "attack4":
                currentAnim = attack4Anim;
                break;

            default:
                System.err.println("Error in Rendering Player... Exiting");
                System.exit(1);
        }

        if(prevState != state && currentAnim.done() || currentAnim.interruptable() && prevState != state) currentAnim.reset();

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
        ATK1(Input.Keys.H),
        ATK2(Input.Keys.J),
        ATK3(Input.Keys.K),
        ATK4(Input.Keys.L);

        public int key;

        private ctrls(int key)
        {
            this.key = key;
        }
    }

}
