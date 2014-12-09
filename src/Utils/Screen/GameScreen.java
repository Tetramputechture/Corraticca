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
import coratticca.Utils.CRandom;
import coratticca.Utils.CPrecache;
import coratticca.Utils.Camera;
import coratticca.Utils.Input;
import coratticca.Utils.Grid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    
    private static final Vector2f size;
    
    private static final Grid grid;

    private static final Color bgColor;

    private static final List<Button> buttons = new ArrayList<>();
    
    private static final List<Entity> entities = new ArrayList<>();
    private static final List<Entity> entsToBeRemoved = new ArrayList<>();
    
    private static Sprite pointerSprite;
    private static Sprite backgroundSprite;
    
    private static final PlayerEntity player = new PlayerEntity();
    
    private static int enemiesKilled;
    private static int numEnemies;
    private static int asteroidsBlasted;
    private static int shotsFired;
    
    private static final int maxEnemies = 25;
    
    private static final Clock gameClock = new Clock();
    private static Clock pauseClock;
    private static float lastTime;
    private static float pauseTime;
    private static boolean isPaused;
    
    static {
        
        // set game map size
        size = new Vector2f(Window.getSize().x * 2, Window.getSize().y * 2);
        
        // set background color
        bgColor = new Color(150, 150, 150);
        
        grid = new Grid(size);
        
        initSprites();
    }

    /**
     * Makes the game screen, resets if needed to.
     * @param resetGame if the game is to be reset or not.
     */
    public GameScreen(boolean resetGame) {
        if (resetGame) {
            resetGame();
        }
    }
    
    private static void resetGame() {
        player.reset();
        player.setPos(new Vector2f(0, 0));
        
        entities.clear();
        entities.add(player);
        
        buttons.clear();
        addButtons();
        
        grid.clear();
        
        enemiesKilled = 0;
        asteroidsBlasted = 0;
        numEnemies = 0;
        shotsFired = 0;
               
        gameClock.restart();
        lastTime = 0;
    }
    
    private static void addButtons() {
        // health text
        buttons.add(new Button(new Vector2f((int)player.getPos().x + 15,
                               (int)player.getPos().y - 15),
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
        
        // player position debug text
        buttons.add(new Button(new Vector2f(80,
                                400),
                                20,
                                String.format("Postion: (%s, %s)", player.getPos().x, player.getPos().y),
                                "fonts/OpenSans-Regular.ttf",
                                Color.WHITE,
                                null,
                                false));
        
        // entiy count debug text
        buttons.add(new Button(new Vector2f(67,
                                450),
                                20,
                                String.format("Entity count: %s", entities.size()),
                                "fonts/OpenSans-Regular.ttf",
                                Color.WHITE,
                                null,
                                false));
    }
    
    private static void updateAndDrawButtons() {
        // update health text
        buttons.get(0).setText(Integer.toString(player.getHealth()));
        buttons.get(0).setPosition(new Vector2f(player.getPos().x + 15, player.getPos().y - 15));
        
        // update score text
        buttons.get(1).setText(String.format("Score: %s", asteroidsBlasted));
        
        // update player position text
        buttons.get(2).setText(String.format("Position: (%.0f, %.0f)", player.getPos().x, player.getPos().y));     
        
        // update entity count text
        buttons.get(3).setText(String.format("Entity count: %s", entities.size()));
        
        for (Button b : buttons) {
            b.draw();
        }
        
    }
    
    /**
     * initializes the various sprites used by the game screen.
     */
    public static void initSprites() {
        Texture pT = CPrecache.getPointerTexture();
        pointerSprite = new Sprite(pT);
        pointerSprite.setOrigin(Vector2f.div(new Vector2f(pT.getSize()), 2));
        
        // init background sprite
        Texture bT = CPrecache.getStarfieldTexture();
        backgroundSprite = new Sprite(bT);
        backgroundSprite.setOrigin(Vector2f.div(new Vector2f(bT.getSize()), 2));
    }

    @Override
    public void show() {

        Camera.handleEdges();
        Window.getWindow().setView(Camera.getView());
        Window.getWindow().clear(bgColor);
        
        // use the custom mouse sprite
        Window.getWindow().setMouseCursorVisible(false);
        
        // set delta time
        float currentTime = gameClock.getElapsedTime().asSeconds();
        float dt = currentTime - lastTime;
        
        // if game was paused, subtract the time paused from dt only on the first
        // frame out of pause
        if (pauseTime != 0) {
            dt -= pauseTime;
            pauseTime = 0;
        }
        Window.getWindow().draw(backgroundSprite);
        
        // clear grid
        grid.clear();
        
        checkAndRemoveEntities();
        
        updateEntitiesAndFillGrid(dt);
        
        detectEntityCollisions(dt);
        
        drawEntities();
        
        if (Math.random() < 0.01) {
            new SpawnAsteroidAction(CRandom.getRandomEdgeVector(), CRandom.randInt(1, 4)).execute();
        }
        
        updateAndDrawButtons();
        
        // set pointer position
        pointerSprite.setPosition(Input.getMousePos());
        
        Window.getWindow().draw(pointerSprite);
        Window.getWindow().display();
        
        lastTime = currentTime;
        
    }
    
    private static void checkAndRemoveEntities() {
        // reset removal list
        
        entsToBeRemoved.clear();
        for (Entity e : entities) {
            if (e.toBeRemoved()) {
                entsToBeRemoved.add(e);
            }
        }
        
        for (Entity e : entsToBeRemoved) {
            e.handleRemoval();
            entities.remove(e);
        }
    }
    
    private static void updateEntitiesAndFillGrid(float dt) {
        for (Entity e : entities) {
            e.update(dt);
            grid.addEntity(e);
        }
    }
    
    private static void detectEntityCollisions(float dt) {
        for (Entity e : entities) {
            e.detectCollisions(dt);
        }
    }
    
    private static void drawEntities() {
        for (Entity e : entities) {
            e.draw();
        }
    }
    
    public static void pauseGame() {
        pauseClock = new Clock();
        pauseTime = 0;
    }
    
    public static void resumeGame() {
        if (pauseClock != null) {
            pauseTime = pauseClock.getElapsedTime().asSeconds();
        } else {
            pauseTime = 0;
        }
    }
    
    /**
     *
     * @return
     */
    public static Vector2f getBounds() {
        return size;
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
    
    /**
     *
     * @return
     */
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
    
    public static int getAsteroidsBlasted() {
        return asteroidsBlasted;
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
        return (float)asteroidsBlasted/shotsFired;
    }
    
    /**
     * Increments enemies killed.
     */
    public static void killEnemy() {
        enemiesKilled++;
    }
    
    public static void scorePoint() {
        asteroidsBlasted++;
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
    }
    
    public static void addEntityToGrid(Entity e) {
        grid.addEntity(e);
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
    
    public static Grid getGrid() {
        return grid;
    }
    
    @Override
    public ScreenName getName() {
        return name;
    }
}
