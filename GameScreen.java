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

    private static final Color bgColor;

    private final List<Button> buttons;
    
    private static List<Entity> entities;
    
    private int numEnemies;
    private static int maxEnemies;
    
    private final Sprite pointerSprite;
    
    private static PlayerEntity playerEntity;
    
    private final Clock clock;
    private float lastTime;
    
    static {
        bgColor = new Color(150, 150, 150);
    }

    /**
     * Makes the game screen, resets if needed to.
     * @param resetGame if the game is to be reset or not.
     */
    public GameScreen(boolean resetGame) {
        
        if (resetGame) {
            entities = new ArrayList<>();
        }
        buttons = new ArrayList<>();
        
        maxEnemies = 100;
       
        // init mouse sprite
        Texture pointerTexture = new Texture();
        try {
            pointerTexture.loadFromFile(Paths.get("pointer.png"));
        } catch (IOException ex) {
            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        pointerSprite = new Sprite(pointerTexture);
        
        if (resetGame) {
            playerEntity = new PlayerEntity(PlayerEntity.getSprite());
            addEntity(playerEntity);
        }
        
        // health text
        buttons.add(new Button(100,
                               50,
                               30,
                               "Health: " + playerEntity.getHealth(),
                               "OpenSans-Regular.ttf",
                               Color.BLACK,
                               new UnassignedAction()));
        
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
        
        // iterate through entities
        for (Iterator<Entity> it = entities.iterator(); it.hasNext(); ) {
            Entity e = it.next();
            e.draw();
            e.update(dt);
            if (e.remove()) {
                if (e.toString().equals("Player")) {
                    System.out.println("Game lost!");
                    Window.changeScreen(new GameLostScreen());
                }
                if (e.toString().equals("Enemy")) {
                    numEnemies--;
                    it.remove();
                } else {
                    it.remove();
                }
            }
        }
        
        if (numEnemies <= maxEnemies) {
            if (Math.random() < 0.1) {
                new SpawnEnemyAction().execute();
                numEnemies++;
            }
        }
        
        // set pointer position
        pointerSprite.setPosition(Input.getMousePos());
        
        // show health text
        buttons.get(0).setText("Health: " + Integer.toString(playerEntity.getHealth()));
        buttons.get(0).draw();
        
        Window.getWindow().draw(pointerSprite);
        Window.getWindow().display();
        
        lastTime = currentTime;
    }
    
    public static PlayerEntity getCurrentPlayer() {
        return playerEntity;
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
    
    /**
     * Adds an entity to the screen.
     * @param e the entity to add.
     */
    public static void addEntity(Entity e) {
        entities.add(e);
        System.out.format("Entity count: %s\n", entities.size());
    }
    
    public static List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    @Override
    public String toString() {
        return "GAME";
    }
}
