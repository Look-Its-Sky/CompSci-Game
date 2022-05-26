package com.judeandsyrus.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;

public class Anim
{
    private ArrayList<Sprite> arr;
    private TextureAtlas ta;
    private int count;
    private int speedCount;
    private int speed;

    public int h, w;

    public Anim(int speed, String path, int length)
    {
        count = 0;
        this.speed = speed;
        speedCount = 0;

        w = 50;
        h = 50;

        arr = new ArrayList<Sprite>();

        //Make a new TextureAtlas if fails... uh it shits itself and dies lmao... safely
        try
        {
            ta = new TextureAtlas(path);
        }

        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        /*
        Basically all files follow this name convention

        <path>
            f0.png
            f1.png
            ..

        <some other class in another object>
            f0.png
            f1.png
            ..
         */

        for(int i = 0; i <= length - 1; i++)
        {
            arr.add(ta.createSprite("f" + i));
        }
    }

    public void add(String s)
    {
        arr.add(ta.createSprite(s));
    }

    public void remove(int i) {
        arr.remove(i);
    }

    public void remove(Sprite s)
    {
        arr.remove(s);
    }

    public void nextFrame()
    {
        count++;
    }

    public void prevFrame()
    {
        count--;
    }

    //This actually should work since it runs at 60fps and not like *cough* swing
    public Sprite currentSprite()
    {
        speedCount++;

        if(speedCount > speed)
        {
            speedCount = 0;

            if(count >= arr.size() - 1) count = 0;
            else count++;
        }

        arr.get(count).setScale(10,10);

        return arr.get(count);
    }

    //Reverse Bob the builder... bob the destroyer😈.. get it cause it's a- nvm
    @Override
    protected void finalize() throws Throwable
    {
        ta.dispose();
    }
}