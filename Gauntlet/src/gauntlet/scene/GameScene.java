package gauntlet.scene;

import gauntlet.Gauntlet;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class GameScene extends GauntletScene {

    public static String CHARACTER_PATH = "img/warrior.png";
    private Image imgCharacter;

    public GameScene() 
    {
        super();
        
        try 
        {
            imgCharacter = new Image(Files.newInputStream(
                    Paths.get(CHARACTER_PATH)));  
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
