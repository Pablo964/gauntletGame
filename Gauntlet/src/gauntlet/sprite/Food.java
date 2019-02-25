package gauntlet.sprite;

public class Food extends StaticSprite
{
    protected int energy;

    public Food(int energy) 
    {
        this.energy = energy;
    }

    public int getEnergy() 
    {
        return energy;
    }
    
}
