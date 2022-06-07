package com.judeandsyrus.game;

import java.lang.Math;

public class Enemy extends Entity
{
    public Enemy(int x, int y)
    {
        this.x = x;
        this.y = y;

        currentAnim = new Anim(5, "enemy/spritesheet.txt", 6);
    }

    public void move(Player p)
    {
        /*
        Path finding - NOTE: this was intentional idk if the difference is negligible than just using the functions raw

        But i wanna work with actual past tense coordinates rather than if y isnt updated at the same speed x is
        Since yk these functions literally grab the x and y coordinates then serve it to us
        OOP things i think or idk if i can blame that on OOP or im just overthinking this
        Im losing time sooooo
        */


        int pX = p.returnX();
        int pY = p.returnY();

        int xDist = absVal(this.x - pX);
        int yDist = absVal(this.y - pY);

        /*
        Literally just check which way will move closer to the player then move that way lmao
        Maybe i could add some more advanced path finding in the future but im tired and ready to just add this
        Thank mr ross for his physics test :)
        */

        //Find theoretical best x
        if(xDist > absVal(this.x + spd - pX))
        {
            x += spd;
            flip = false;
        }

        else if(xDist > absVal(this.x - spd - pX))
        {
            x -= spd;
            flip = true;
        }

        //Find theoretical best y
        if(yDist > absVal(this.y + spd - pY)) y += spd;
        else if(yDist > absVal(this.y - spd - pX)) y -= spd;

    }

    //Wow i actually had to use math in computer science -- lmfao edit ill use this later
    private double getDistance(int x1, int y1, int x2, int y2)
    {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private int absVal(int i)
    {
        if(i < 0) return i * -1;
        return i;
    }

    public boolean isDead()
    {
        if(hlth <= 0) return true;
        else return false;
    }
}
