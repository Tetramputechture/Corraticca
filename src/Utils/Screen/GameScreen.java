/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils.Screen;

import coratticca.Actions.SpawnAsteroidAction;
import coratticca.Entities.EnemyShipEntity;
import coratticca.Entities.PlayerEntity;
import coratticca.Entities.Entity;
import coratticca.Utils.Window;
import coratticca.Utils.Button;
import coratticca.Utils.Camera;
import coratticca.Utils.Input;
import coratticca.Actions.SpawnEnemyAction;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

/**
 * The screen for the game.
 * @author Nick
 */
public final class GameScreen implements Screen {
    
    private static final ScreenName name = ScreenName.GAME_SCREEN;
    
    private static final Vector2f bounds;

    private static final Color bgColor;

    private final List<Button> buttons;
    
    private static final List<Entity> entities = new ArrayList<>();
    private static final List<Entity> entsToBeRemoved = new ArrayList<>();
    
    private static Sprite pointerSprite;
    private static Sprite backgroundSprite;
    
    private static final PlayerEntity player = new PlayerEntity();
    
    private static int enemiesKilled;
    private static int numEnemies;
    private static int shotsFired;
    
    private static final int maxEnemies = 25;
    
    private final Clock clock;
    private float lastTime;
    
    static {
        
        // set game map bounds
        bounds = new Vector2f(Window.getSize().x * 2, Window.getSize().y * 2);
        
        // set background color
        bgColor = new Color(150, 150, 150);
        
        initSprites();
    }

    /**
     * Makes the game screen, resets if needed to.
     * @param resetGame if the game is to be reset or not.
     */
    public GameScreen(boolean resetGame) {
        
        if (resetGame) {
            entities.clear();
            player.reset();
            enemiesKilled = 0;
            numEnemies = 0;
            shotsFired = 0;
        }
        buttons = new ArrayList<>();

        
        // health text
        buttons.add(new Button(new Vector2f((int)player.getPos().x - 10,
                               (int)player.getPos().y - 10),
                               20,
                               Integer.toString(player.getHealth()),
                               "fonts/OpenSans-Regular.ttf",
                               Color.WHITE,
                               null,
                               true));
        
        // score text
        buttons.add(new Button(new Vector2f(50,
                               25),
                               20,
                               String.format("Score: %s", enemiesKilled),
                               "fonts/OpenSans-Regular.ttf",
                               Color.WHITE,
                               null,
                               false));
       
        clock = new Clock();
        lastTime = 0;
    }
    
    public static void initSprites() {
        // init mouse sprite
        Texture pointerTexture = new Texture();
        try {
            pointerTexture.loadFromFile(Paths.get("sprites/pointer.png"));
        } catch (IOException ex) {
            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        pointerSprite = new Sprite(pointerTexture);
        
        // init background sprite
        Texture backgroundTexture = new Texture();
        try {
            backgroundTexture.loadFromFile(Paths.get("sprites/starfield.png"));
        } catch (IOException ex) {
            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setOrigin(Vector2f.div(new Vector2f(backgroundTexture.getSize()), 2));
    }

    /**
     * shows the screen.
     */
    @Override
    public void show() {

        Camera.handleEdges();
        Window.getWindow().setView(Camera.getView());
        Window.getWindow().clear(bgColor);
        
        // use the custom mouse sprite
        Window.getWindow().setMouseCursorVisible(false);

        // set delta time
        float currentTime = clock.getElapsedTime().asSeconds();
        float dt = currentTime - lastTime;
          
        Window.getWindow().draw(backgroundSprite);
        
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
        
        // draw player last so bullets appear to come out of player (not from center)
        player.draw();
        player.update(dt);
        
        // check if player has no health
        player.toBeRemoved();
        
        for (Entity e : entsToBeRemoved) {
            if (e instanceof EnemyShipEntity) {
                numEnemies--;
                ((EnemyShipEntity)e).spawnDeathParticle();
            }
            entities.remove(e);
        }
        
        if (Math.random() < 0.02) {
            new SpawnAsteroidAction().execute();
        }
        
//        if (Math.random() < 0.02) {
//            new SpawnEnemyAction().execute();
//        }
        
        // set pointer position
        pointerSprite.setPosition(Input.getMousePos());
        

        
        // update health text
        buttons.get(0).setText(Integer.toString(player.getHealth()));
        buttons.get(0).setPosition(new Vector2f(player.getPos().x + 15, player.getPos().y - 15));
        buttons.get(0).draw();
        
        // update score text
        buttons.get(1).setText(String.format("Score: %s", enemiesKilled));
        buttons.get(1).draw();
        
        Window.getWindow().draw(pointerSprite);
        Window.getWindow().display();
        
        lastTime = currentTime;
    }
    
    public static Vector2f getBounds() {
        return bounds;
    }
    
    /**
     * Gets the current player entity on the screen.
     * @return the current screen's player entity.
     */
    public static PlayerEntity getCurrentPlayer() {
        return player;
    }
    
    /**
     * Gets the screen's background color.
     * @return the screen's background color.
     */
    public static Color getBGColor() {
        return bgColor;
    }
    
    public Image getBGImage() {
        return Window.getWindow().capture();
    }
    /**
     * Gets the total enemies killed.
     * @return the total number of enemies killed.
     */
    public static int getEnemiesKilled() {
        return enemiesKilled;
    }
    
    /**
     * Gets the total shots fired from the player.
     * @return the total number of bullets fired.
     */
    public static int getShotsFired() {
        return shotsFired;
    }
    
    /**
     * Gets the accuracy of the player (enemies killed / bullets shot)
     * @return the player's shooting accuracy
     */
    public static double getAccuracy() {
        return (float)enemiesKilled/shotsFired;
    }
    
    /**
     * Increments enemies killed.
     */
    public static void killEnemy() {
        enemiesKilled++;
    }
    
    /**
     * Increments shots fired.
     */
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
    
    /**
     * Gets the current game screen's entities
     * @return the screen's entities as an unmodifiable list
     */
    public static List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }
    
    @Override
    public List<Button> getButtons() {
        return Collections.unmodifiableList(buttons);
    }
    
    @Override
    public ScreenName getName() {
        return name;
    }
}
