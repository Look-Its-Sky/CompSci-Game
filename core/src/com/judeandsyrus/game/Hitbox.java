package com.judeandsyrus.game;

public class Hitbox
{
    private int x1, y1, x2, y2;

    public Hitbox(int x1, int y1, int x2, int y2)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public boolean doesCollide(Entity e)
    {
        int eX1 = e.returnX();
        int eX2 = e.returnX() + e.returnW();
        int eY1 = e.returnY();
        int eY2 = e.returnY() + e.returnH();

        if(((this.x1 > eX1 && this.x1 < eX2) || (this.x2 > eX1 && this.x2 < eX2)) && ((this.y1 > eY1 && this.y1 < eY2) || (this.y2 > eY1 && this.y2 < eY2))) return true;
        else return false;
    }
}
