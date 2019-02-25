package gauntlet.sprite;

public class Treasure extends StaticSprite
{
    static final int TREASURE_SCORE = 100;

    public Treasure(int x, int y) 
    {
        this.x = x;
        this.y = y;
        this.spriteX = 1198;
        this.spriteY = 340;
    }
    
    public int getPointsTreasure() 
    {
        return TREASURE_SCORE;
    }
}
