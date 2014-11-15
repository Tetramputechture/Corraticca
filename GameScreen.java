/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;

/**
 * The screen for the game.
 * @author Nick
 */
public final class GameScreen implements Screen {
    
    private ScreenName name = ScreenName.GAME_SCREEN;

    private static final Color bgColor;

    private final List<Button> buttons;
    
    private static final List<Entity> entities = new ArrayList<>();
    private static final List<Entity> entsToBeRemoved = new ArrayList<>();
    
    private static final Sprite pointerSprite;
    
    private static final PlayerEntity player = new PlayerEntity();
    
    private static int enemiesKilled;
    private static int shotsFired;
    
    private final Clock clock;
    private float lastTime;
    
    static {
        // set background color
        bgColor = new Color(150, 150, 150);
        
        // init mouse sprite
        Texture pointerTexture = new Texture();
        try {
            pointerTexture.loadFromFile(Paths.get("pointer.png"));
        } catch (IOException ex) {
            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        pointerSprite = new Sprite(pointerTexture);
    }

    /**
     * Makes the game screen, resets if needed to.
     * @param resetGame if the game is to be reset or not.
     */
    public GameScreen(boolean resetGame) {
        
        if (resetGame) {
            entities.clear();
        }
        buttons = new ArrayList<>();
        
        if (resetGame) {
            player.reset();
            addEntity(player);
        }
        
        // health text
        buttons.add(new Button((int)player.getPos().x - 10,
                               (int)player.getPos().y - 10,
                               20,
                               Integer.toString(player.getHealth()),
                               "OpenSans-Regular.ttf",
                               Color.BLACK,
                               null));
       
        clock = new Clock();
        lastTime = 0;
    }

    /**
     * shows the screen.
     */
    @Override
    public void show() {

        Window.getWindow().clear(bgColor);
        
        // use the custom mouse sprite
        Window.getWindow().setMouseCursorVisible(false);

        // set delta time
        float currentTime = clock.getElapsedTime().asSeconds();
        float dt = currentTime - lastTime;
        
        // reset removal list
        entsToBeRemoved.clear();
        
        // iterate through entities
        for (Entity e : entities) {
            e.draw();
            e.update(dt);
            if (e.toBeRemoved()) {
                entsToBeRemoved.add(e);
            }
        }
        
        for (Entity e : entsToBeRemoved) {
            if (e instanceof EnemyEntity) {
                ((EnemyEntity)e).spawnDeathParticle();
            }
            entities.remove(e);
        }
        
        if (Math.random() < 0.02) {
            new SpawnEnemyAction().execute();
        }
        
        // set pointer position
        pointerSprite.setPosition(Input.getMousePos());
        
        // show health text
        buttons.get(0).setText(Integer.toString(player.getHealth()));
        buttons.get(0).setPosition((int)player.getPos().x + 15, (int)player.getPos().y - 15);
        buttons.get(0).draw();
        
        Window.getWindow().draw(pointerSprite);
        Window.getWindow().display();
        
        lastTime = currentTime;
    }
    
    public static PlayerEntity getCurrentPlayer() {
        return player;
    }

    /**
     * Gets the buttons on the screen.
     * @return the list of buttons on the screen.
     */
    @Override
    public List<Button> getButtons() {
        return Collections.unmodifiableList(buttons);
    }
    
    /**
     * Gets the screen's background color.
     * @return the screen's background color.
     */
    public static Color getBGColor() {
        return bgColor;
    }
    
    public static int getEnemiesKilled() {
        return enemiesKilled;
    }
    
    public static int getShotsFired() {
        return shotsFired;
    }
    
    public static double getAccuracy() {
        return (float)enemiesKilled/shotsFired;
    }
    
    public static void killEnemy() {
        enemiesKilled++;
    }
    
    public static void fireShot() {
        shotsFired++;
    }
    
    /**
     * Adds an entity to the screen.
     * @param e the entity to add.
     */
    public static void addEntity(Entity e) {
        entities.add(e);
        System.out.format("Entity count: %s%n%n", entities.size());
    }
    
    public static List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    @Override
    public String toString() {
        return "GAME";
    }

    @Override
    public ScreenName getName() {
        return name;
    }
}
