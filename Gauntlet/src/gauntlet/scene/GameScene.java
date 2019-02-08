package gauntlet.scene;

import gauntlet.Gauntlet;
import static gauntlet.scene.GauntletScene.GAME_HEIGHT;
import static gauntlet.scene.GauntletScene.GAME_WIDTH;
import gauntlet.sprite.Dwarf;
import gauntlet.sprite.MainCharacter;
import gauntlet.sprite.MovableSprite;
import gauntlet.sprite.Sorcerer;
import gauntlet.sprite.Valkyrie;
import gauntlet.sprite.Warrior;
import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class GameScene extends GauntletScene 
{
    public static String CHARACTER_PATH;
    public static String gameSong;
    protected int randomSong;
    MainCharacter character;
    Font myFont = Font.font("Courier New", FontWeight.BOLD, 24);

    public GameScene() 
    {
        super();
    }

    @Override
    public void draw() 
    {
        activeKeys.clear();
        
        randomSong = (int) (Math.random() * 3) + 1;
        
        switch (randomSong) 
        {
            case 1:
                gameSong = "sound/themeA.mp3";
                break;
            case 2:
                gameSong = "sound/themeB.mp3";
                break;
            case 3:
                gameSong = "sound/themeC.mp3";
                break;
            default:
                break;
        }
        
        sound = new Media(new File(gameSong).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        switch (PlayerSelectScene.chosenPlayer)
        {
            case 0:
                character = new Warrior();
                break;
            case 1:
                character = new Valkyrie();
                break;
            case 2:
                character = new Sorcerer();
                break;
            case 3:
                character = new Dwarf();
                break;
        }
        character.moveTo(100, 200);
        
        new AnimationTimer() 
        {
            @Override
            public void handle(long currentNanoTime) 
            {
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                
                gc.setStroke(Color.YELLOW);
                gc.strokeLine(0, 500, 800, 500);
                
                gc.setFont(myFont);
                gc.setFill(Color.RED);
                gc.fillText("ENERGY: ", 5, 550);
                
                gc.setFont(myFont);
                gc.setFill(Color.GREEN);
                gc.fillText("POINTS: ", 600, 550);
                
                if (activeKeys.contains(KeyCode.SPACE)) 
                {
                    mediaPlayer.stop();
                    this.stop();

                    Gauntlet.setScene(Gauntlet.GAME_OVER_SCENE);
                }
                
                if (activeKeys.contains(KeyCode.LEFT) && 
                            activeKeys.contains(KeyCode.DOWN))
                {
                    character.moveTo(character.getX() - 1,
                            character.getY() + 1);
                    character.animate(MovableSprite.LEFT_DOWN);
                }
                
                else if (activeKeys.contains(KeyCode.LEFT) && 
                            activeKeys.contains(KeyCode.UP))
                {
                    character.moveTo(character.getX() - 1,
                            character.getY() - 1);
                    character.animate(MovableSprite.LEFT_UP);
                }
                
                else if (activeKeys.contains(KeyCode.RIGHT) && 
                            activeKeys.contains(KeyCode.DOWN)) 
                {
                    character.moveTo(character.getX() + 1,
                            character.getY() + 1);
                    character.animate(MovableSprite.RIGHT_DOWN);
                }
                
                else if (activeKeys.contains(KeyCode.RIGHT) && 
                            activeKeys.contains(KeyCode.UP)) 
                {
                    character.moveTo(character.getX() + 1,
                            character.getY() - 1);
                    character.animate(MovableSprite.RIGHT_UP);
                }
                
                else
                {
                    if (activeKeys.contains(KeyCode.UP)) 
                    {
                        character.moveTo(character.getX(),
                                character.getY() - 1);
                        character.animate(MovableSprite.UP);
                    }
                    if (activeKeys.contains(KeyCode.DOWN)) 
                    {
                        character.moveTo(character.getX(),
                                character.getY() + 1);
                        character.animate(MovableSprite.DOWN);
                    }
                    if (activeKeys.contains(KeyCode.LEFT)) 
                    {
                        character.moveTo(character.getX() - 1,
                                character.getY());
                        character.animate(MovableSprite.LEFT);
                    }
                    if (activeKeys.contains(KeyCode.RIGHT))
                    {
                        character.moveTo(character.getX() + 1,
                                character.getY());
                        character.animate(MovableSprite.RIGHT);
                    }
                }
                character.draw(gc);
            }
        }.start();  
    }
}
