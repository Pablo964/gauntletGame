package gauntlet.scene;

import gauntlet.Gauntlet;
import static java.lang.System.gc;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class WelcomeScene extends GauntletScene
{
    public static final String WELCOME_SCREEN_PATH = "img/welcome_screen.png";
    private Image imgBackground;

    public WelcomeScene()
    {
        super();
        
        try
        {
            imgBackground = new Image(Files.newInputStream(
                    Paths.get(WELCOME_SCREEN_PATH)));
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
            public void handle(long currentNanoTime)
            {
                if(activeKeys.contains(KeyCode.SPACE))
                {
                    this.stop();
                    Gauntlet.setScene(Gauntlet.PLAYER_SELECT_SCENE);
                } 
                else if (activeKeys.contains(KeyCode.ESCAPE)) 
                {
                    this.stop();
                    Gauntlet.exit();
                }
                gc.drawImage(imgBackground, 0, 0);
            }
        }.start();
    }
}
