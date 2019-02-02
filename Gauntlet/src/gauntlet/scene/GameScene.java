package gauntlet.scene;

import gauntlet.Gauntlet;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class GameScene extends GauntletScene 
{
    public static String CHARACTER_PATH;
    private Image imgCharacter;
    int HandPositionY;
    
    
    public GameScene() 
    {
        super();     
    }
       
    @Override
    public void draw() 
    {
        activeKeys.clear();
        
        this.HandPositionY = PlayerSelectScene.getPositionY();
       
        if (HandPositionY == 125) 
        {
            CHARACTER_PATH = "img/warrior.png";
        }
        else if (HandPositionY == 230)
        {
            CHARACTER_PATH = "img/valkyrie.png";
        }
        else if (HandPositionY == 335)
        {
            CHARACTER_PATH = "img/sorcerer.png";
        }
        else
        {
            CHARACTER_PATH = "img/dwarf.png";
        }
        
        try 
        {
            imgCharacter = new Image(Files.newInputStream(
                    Paths.get(CHARACTER_PATH)));  
        }
        catch (Exception e) 
        {
        }
        new AnimationTimer() 
        {
            public void handle(long currentNanoTime) {
                // Black background
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

                if (activeKeys.contains(KeyCode.SPACE)) 
                {
                    this.stop();
                    Gauntlet.setScene(Gauntlet.GAME_OVER_SCENE);
                }
                
                gc.drawImage(imgCharacter, 100, 200);
            }
        }.start();  
    }
}
