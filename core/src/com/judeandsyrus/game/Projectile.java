package com.judeandsyrus.game;

import java.util.ArrayList;

public class Projectile extends Entity
{
    private ArrayList<Hitbox> hitboxes;

    public Projectile(int x, int y)
    {
        this.x = x;
        this.y = y;

        w = 10;
        h = 10;

        hitboxes = new ArrayList<Hitbox>(); //its an arraylist for multiple hitboxes duh
        hitboxes.add(new Hitbox(x, y, x + w, y + h));
    }

    public ArrayList<Hitbox> returnHitboxes()
    {
        return hitboxes;
    }
}
