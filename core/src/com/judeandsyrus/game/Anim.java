package com.judeandsyrus.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import javax.swing.*;
import java.util.ArrayList;

public class Anim
{
    private ArrayList<Sprite> arr;
    private int count;
    private int speedCount;
    private int speed;

    public Anim(int speed)
    {
        arr = new ArrayList<Sprite>();

        count = 0;
        this.speed = speed;
        speedCount = 0;
    }

    public void add(Sprite s)
    {
        arr.add(s);
    }

    public void remove(int i)
    {
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

    public Sprite returnCurrentImg()
    {
        if(speedCount >= speed)
        {
            speedCount = 0;

            if(count + 1 > arr.size()) count = 0;
            else count++;
        }

        return arr.get(count);
    }
}
