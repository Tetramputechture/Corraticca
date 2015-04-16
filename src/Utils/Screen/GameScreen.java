/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils.Screen;

import coratticca.Actions.SpawnAsteroidAction;
import coratticca.Entities.PlayerEntity;
import coratticca.Entities.Entity;
import coratticca.Utils.Button;
import coratticca.Utils.CPhysics;
import coratticca.Utils.CRandom;
import coratticca.Utils.CPrecache;
import coratticca.Utils.Camera;
import coratticca.Utils.Grid;
import coratticca.Utils.Window;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

/**
 * The screen for the game.
 *
 * @author Nick
 */
public final class GameScreen extends Screen {

    private final CPhysics physicsHandler;

    private final Camera camera;

    private final CRandom rand;

    private final Vector2f size;

    private final Grid grid;

    private final List<Entity> entities = new LinkedList<>();
    private final List<Entity> entsToBeRemoved = new LinkedList<>();

    private Sprite pointerSprite;
    private Sprite backgroundSprite;

    private PlayerEntity player;
    private final int playerHealthIconOffset = 15;

    private int enemiesKilled;
    private int asteroidsBlasted;
    private int shotsFired;

    private final Clock gameClock;
    private Clock pauseClock;
    private float lastTime;
    private float pauseTime;

    public GameScreen(Window w) {
        super(w);
        physicsHandler = new CPhysics();
        camera = new Camera(super.window);
        rand = new CRandom();
        gameClock = new Clock();
        size = new Vector2f(super.window.getSize().x * 2, super.window.getSize().y * 2);
        super.setBgColor(new Color(150, 150, 150));
        grid = new Grid(size);
        initSprites();
    }

    public void initPlayer() {
        player = new PlayerEntity(this, Vector2f.ZERO);
        entities.add(player);
        camera.setPos(player.getPos());
    }

    public void initButtons() {
        // health text
        buttons.add(new Button(window,
                new Vector2f((int) player.getPos().x + playerHealthIconOffset,
                        (int) player.getPos().y - playerHealthIconOffset),
                20,
                Integer.toString(player.getHealth()),
                "fonts/OpenSans-Regular.ttf",
                Color.WHITE,
                null,
                camera));

        // score text
        buttons.add(new Button(window,
                new Vector2f(50,
                        25),
                20,
                String.format("Score: %s", enemiesKilled),
                "fonts/OpenSans-Regular.ttf",
                Color.WHITE,
                null));

        // player position debug text
        buttons.add(new Button(window,
                new Vector2f(80,
                        425),
                20,
                String.format("Postion: (%s, %s)", player.getPos().x, player.getPos().y),
                "fonts/OpenSans-Regular.ttf",
                Color.WHITE,
                null));

        // entiy count debug text
        buttons.add(new Button(window,
                new Vector2f(67,
                        460),
                20,
                String.format("Entity count: %s", entities.size()),
                "fonts/OpenSans-Regular.ttf",
                Color.WHITE,
                null));
    }

    /**
     * initializes the various sprites used by the game screen.
     */
    public void initSprites() {
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

        camera.handleEdges(this);
        window.getRenderWindow().setView(camera.getView());
        window.getRenderWindow().clear(super.getBgColor());

        // use the custom mouse sprite
        window.getRenderWindow().setMouseCursorVisible(false);

        // set delta time
        float currentTime = gameClock.getElapsedTime().asSeconds();
        float dt = currentTime - lastTime;

        // if game was paused, subtract the time paused from dt only on the first
        // frame out of pause
        if (pauseTime != 0) {
            System.out.println("what");
            dt -= pauseTime;
            pauseTime = 0;
        }

        window.getRenderWindow().draw(backgroundSprite);

        // clear grid
        grid.clear();

        checkAndRemoveEntities();

        updateEntitiesAndFillGrid(dt);

        detectEntityCollisions(dt);

        drawEntities();

        if (Math.random() < 0.01) {
            new SpawnAsteroidAction(rand.getRandomEdgeVector(camera), rand.randInt(1, 3)).execute(window);
        }

        updateAndDrawButtons();

        // set pointer position
        pointerSprite.setPosition(window.getInputHandler().getMousePos());

        window.getRenderWindow().draw(pointerSprite);
        window.getRenderWindow().display();

        lastTime = currentTime;
    }

    public CPhysics getPhysicsHandler() {
        return physicsHandler;
    }

    private void checkAndRemoveEntities() {

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

    private void updateEntitiesAndFillGrid(float dt) {
        for (Entity e : entities) {
            e.update(dt);
            grid.addEntity(e);
        }
    }

    private void detectEntityCollisions(float dt) {
        for (Entity e : entities) {
            e.detectCollisions(dt);
        }
    }

    private void drawEntities() {
        for (Entity e : entities) {
            e.draw();
        }
    }

    private void updateAndDrawButtons() {
        // update health text
        buttons.get(0).setText(Integer.toString(player.getHealth()));
        buttons.get(0).setPosition(new Vector2f(player.getPos().x + playerHealthIconOffset, player.getPos().y - playerHealthIconOffset));

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

    public void pause() {
        pauseClock = new Clock();
        pauseTime = 0;
    }

    public void resume() {
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
    public Vector2f getBounds() {
        return size;
    }

    /**
     * Gets the current player entity on the screen.
     *
     * @return the current screen's player entity.
     */
    public PlayerEntity getCurrentPlayer() {
        return player;
    }

    /**
     *
     * @return
     */
    public Image getBGImage() {
        return window.getRenderWindow().capture();
    }

    /**
     * Gets the total enemies killed.
     *
     * @return the total number of enemies killed.
     */
    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    public int getAsteroidsBlasted() {
        return asteroidsBlasted;
    }

    /**
     * Gets the total shots fired from the player.
     *
     * @return the total number of bullets fired.
     */
    public int getShotsFired() {
        return shotsFired;
    }

    /**
     * Gets the accuracy of the player (enemies killed / bullets shot)
     *
     * @return the player's shooting accuracy
     */
    public double getAccuracy() {
        return (float) asteroidsBlasted / shotsFired;
    }

    /**
     * Increments enemies killed.
     */
    public void killEnemy() {
        enemiesKilled++;
    }

    public void scorePoint() {
        asteroidsBlasted++;
    }

    /**
     * Increments shots fired.
     */
    public void fireShot() {
        shotsFired++;
    }

    /**
     * Adds an entity to the screen.
     *
     * @param e the entity to add.
     */
    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void addEntityToGrid(Entity e) {
        grid.addEntity(e);
    }

    /**
     * Gets the current game screen's entities
     *
     * @return the screen's entities as an unmodifiable list
     */
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public ScreenName getName() {
        return ScreenName.GAME_SCREEN;
    }
}
