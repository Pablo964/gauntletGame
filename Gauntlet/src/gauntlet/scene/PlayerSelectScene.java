package gauntlet.scene;

import gauntlet.Gauntlet;
import static java.lang.System.gc;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class PlayerSelectScene extends GauntletScene
{
    public static final String PLAYER_SELECT_SCREEN_PATH = 
            "img/player_select_screen.png"; 
    
    public static final String CHOOSE_PLAYER_PATH = 
            "img/choose_player.png"; 
    
    private Image imgBackground;
    private Image imgHand;
    
    public PlayerSelectScene()
    {
        super();
    
        try
        {
            imgBackground = new Image(Files.newInputStream(
                    Paths.get(PLAYER_SELECT_SCREEN_PATH)));
            
            imgHand = new Image(Files.newInputStream(
                    Paths.get(CHOOSE_PLAYER_PATH)));
        } 
        catch (Exception e) 
        {
        }     
    }
    
    @Override
    public void draw()
    {
        activeKeys.clear();
        
        new AnimationTimer()
        {
            int positionX = 510;
            int positionY = 125;
            public void handle(long currentNanoTime) 
            {
                
                
                if (activeKeys.contains(KeyCode.SPACE))
                {
                    this.stop();
                    Gauntlet.setScene(Gauntlet.GAME_SCENE);
                }

                if (releasedKeys.contains(KeyCode.UP) && positionY > 125) 
                {
                    releasedKeys.remove(KeyCode.UP);
                    positionY -= 105;
                    
                }
                else if (releasedKeys.contains(KeyCode.DOWN) && positionY < 440) 
                {
                    
                    positionY += 105;
                    releasedKeys.remove(KeyCode.DOWN);
                }

                gc.drawImage(imgBackground, 0, 0);
                gc.drawImage(imgHand, positionX, positionY);
            }
        }.start();
    }
}
