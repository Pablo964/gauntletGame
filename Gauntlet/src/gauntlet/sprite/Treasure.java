package gauntlet.sprite;

public class Treasure extends StaticSprite
{
    static final int TREASURE_SCORE = 100;

    public Treasure(int x, int y) 
    {
        this.x = x;
        this.y = y;
        this.spriteX = 1390;
        this.spriteY = 340;
    }
    
    public int getPoints() 
    {
        return TREASURE_SCORE;
    }
}
