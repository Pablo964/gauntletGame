package gauntlet.scene;

import gauntlet.Gauntlet;
import gauntlet.sprite.CaveDweller;
import gauntlet.sprite.Dwarf;
import gauntlet.sprite.Enemy;
import gauntlet.sprite.ExitPoint;
import gauntlet.sprite.Food;
import gauntlet.sprite.Ghost;
import gauntlet.sprite.MainCharacter;
import gauntlet.sprite.Meat;
import gauntlet.sprite.MovableSprite;
import gauntlet.sprite.Potion;
import gauntlet.sprite.Sorcerer;
import gauntlet.sprite.Sprite;
import gauntlet.sprite.Treasure;
import gauntlet.sprite.Valkyrie;
import gauntlet.sprite.Wall;
import gauntlet.sprite.Warrior;
import gauntlet.sprite.Weapon;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Scene to draw the welcome screen
 * @author nacho
 */
public class GameScene extends GauntletScene
{   
    public static final int BOTTOM_LIMIT = 500;
    
    public static String[] GAME_SONGS = { 
        "sound/themeA.mp3",
        "sound/themeB.mp3",
        "sound/themeC.mp3"
    };
    
    private MainCharacter character;
    private Font gameFont;
    private Random random;
    
    private Wall wall;
    private List<Enemy> enemies;
    private List<Food> food;
    private List<Treasure> treasure;
    
    private ExitPoint exit;
    
    private int oldX, oldY, oldXEnemy, oldYEnemy;
    
    private static final long ENERGY_UPDATE = 1000000000;
    private static final int ENERGY_DECREASE = 10;
    private static final int POINTS_INCREASED = 10;
    
    private long currentTime = 0;
       
    public GameScene()
    {        
        super();        
        random = new Random();
        gameFont = Font.font("Courier New", FontWeight.BOLD, 24);
        gc.setFont(gameFont);
        
        wall = new Wall(500, 200);
        
        enemies = new ArrayList<>();
        Enemy enemy = new Ghost();
        enemy.moveTo(700, 200);
        Enemy enemy2 = new Ghost();
        Enemy enemy3 = new CaveDweller();
        enemy3.moveTo(700, 100);
        enemy.moveTo(600, 400);
        enemies.add(enemy);
        enemies.add(enemy2);
        enemies.add(enemy3);
        exit = new ExitPoint(200, 300);
        
        food = new ArrayList<>();
        food.add(new Potion(100, 300));
        food.add(new Meat(50, 50));
        
        treasure = new ArrayList<>();
        treasure.add(new Treasure(400, 253));
    }

    @Override
    public void draw() 
    {
        activeKeys.clear();
        sound = new Media(new File(GAME_SONGS[random.nextInt(GAME_SONGS.length)]).toURI().toString());
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
            public void handle(long currentNanoTime)
            {
                if (currentTime == 0)
                {
                    currentTime = currentNanoTime;
                }
                if (currentNanoTime - currentTime > ENERGY_UPDATE) 
                {
                    currentTime = currentNanoTime;
                    updateEnergy();
                }
                // Black background
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                
                // Bottom texts and line
                
                gc.setStroke(Color.YELLOW);
                gc.strokeLine(0, BOTTOM_LIMIT,
                    GauntletScene.GAME_WIDTH, BOTTOM_LIMIT);
                drawEnergy();
                drawPoints();
                
                
                if(activeKeys.contains(KeyCode.ESCAPE)||
                        character.getEnergy() <= 0)
                {
                    this.stop();
                    mediaPlayer.stop();
                    Gauntlet.setScene(Gauntlet.GAME_OVER_SCENE);
                }
                if (releasedKeys.contains(KeyCode.SPACE))
                {
                    releasedKeys.remove(KeyCode.SPACE);
                    character.addWeapon();
                }
                
                oldX = character.getX();
                oldY = character.getY();

                moveCharacter();
                character.moveWeapons();
                
                List<Weapon> weapons = character.getWeapons();
                int i = 0;
                int j = 0;
                while (i < weapons.size()) 
                {
                    if (weapons.get(i).collidesWith(wall)
                            || weapons.get(i).isOutOfBounds())
                    {
                        character.removeWeapon(i);
                    }
                    else
                    {
                        j = 0;
                        while(j < enemies.size())
                        {
                            if (weapons.get(i).collidesWith(enemies.get(j)))
                            {
                                character.setPoints(
                                        treasure.get(i).getPointsTreasure());
                                drawPoints();
                                enemies.remove(j);
                                character.removeWeapon(i);
                            }
                            else
                            {
                                j++;
                            }
                        }
                    }
                    i++;
                }
                
                while (i < food.size()) 
                {
                    if (character.collidesWith(food.get(i))) {
                        character.setEnergy(food.get(i).getEnergy());
                        drawEnergy();
                        food.remove(i);
                    } 
                    else 
                    {
                        i++;
                    }
                }
                
                if (character.collidesWith(exit)) 
                {
                    this.stop();
                    mediaPlayer.stop();
                    Gauntlet.setScene(Gauntlet.GAME_OVER_SCENE);
                }
                
                if (character.collidesWith(wall)) 
                {
                    character.moveTo(oldX, oldY);
                }
                
                i = 0;
                while (i < treasure.size()) 
                {
                    if (character.collidesWith(treasure.get(i))) 
                    {
                        character.setPoints(treasure.get(i).getPointsTreasure());
                        drawPoints();
                        treasure.remove(i);
                    } 
                    else 
                    {
                        i++;
                    }
                }

                for (Enemy enemy : enemies) 
                {
                    oldXEnemy = enemy.getX();
                    oldYEnemy = enemy.getY();
                    enemy.move(character);
                    
                    if (enemy.collidesWith(wall)) 
                    {
                        enemy.moveTo(oldXEnemy, oldYEnemy);
                    }
                    if (enemy.collidesWith(character)) 
                    {
                        enemy.moveTo(oldXEnemy, oldYEnemy);
                        character.moveTo(oldX, oldY);
                    }
                }
                
                wall.draw(gc);
                exit.draw(gc);
                character.draw(gc);
                
                for (Enemy enemy : enemies) 
                {
                    enemy.draw(gc);
                }
                for (Weapon w: character.getWeapons()) 
                {
                    w.draw(gc);
                }
                for (Food f: food)
                {
                    f.draw(gc);
                }
                
                for (Treasure t: treasure)
                {
                    t.draw(gc);
                }
            }
        }.start();        
    }  
    
    private void moveCharacter()
    {
        boolean left = activeKeys.contains(KeyCode.LEFT);
        boolean right = activeKeys.contains(KeyCode.RIGHT);
        boolean up = activeKeys.contains(KeyCode.UP);
        boolean down = activeKeys.contains(KeyCode.DOWN);

        if (up && character.getY() > 0)
        {
            character.moveTo(character.getX(), 
                             character.getY() - character.STEP_LENGTH );
        }
        if (down &&
                character.getY() < BOTTOM_LIMIT - Sprite.SPRITE_HEIGHT)
        {
            character.moveTo(character.getX(), 
                             character.getY() + character.STEP_LENGTH);
        }
        if (left && character.getX() > 0)
        {
            character.moveTo(character.getX() - character.STEP_LENGTH, 
                             character.getY());
        }
        if (right && character.getX() < GAME_WIDTH - Sprite.SPRITE_WIDTH)
        {
            character.moveTo(character.getX() + character.STEP_LENGTH, 
                             character.getY());
        }        
        
        if (left)
            if (up) character.animate(MovableSprite.LEFT_UP);
            else if (down) character.animate(MovableSprite.LEFT_DOWN);
            else character.animate(MovableSprite.LEFT);
        else if (right)
            if (up) character.animate(MovableSprite.RIGHT_UP);
            else if (down) character.animate(MovableSprite.RIGHT_DOWN);
            else character.animate(MovableSprite.RIGHT);
        else if (up)
            character.animate(MovableSprite.UP);
        else if (down)
            character.animate(MovableSprite.DOWN);
    }
    
    private void drawEnergy()
    {
        gc.setFill(Color.RED);
        gc.fillText("ENERGY: " + character.getEnergy(), 5, BOTTOM_LIMIT + 50);
    }
    
    private void updateEnergy()
    {
        int totalDecrease = ENERGY_DECREASE;
        character.setEnergy(-totalDecrease);
    }
    
    private void drawPoints()
    {
        gc.setFill(Color.GREEN);
        gc.fillText("POINTS: " + character.getPoints(), 
                GauntletScene.GAME_WIDTH - 200, BOTTOM_LIMIT + 50);
    }
    
}
