package coratticca.util.screen;

import coratticca.entity.AsteroidEntity;
import coratticca.entity.BulletEntity;
import coratticca.entity.PlayerEntity;
import coratticca.entity.Entity;
import coratticca.util.PhysicsHandler;
import coratticca.util.RandomUtils;
import coratticca.util.Precache;
import coratticca.util.Camera;
import coratticca.util.Grid;
import coratticca.util.Window;
import coratticca.util.widget.Label;
import coratticca.util.widget.Widget;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;

/**
 * The screen for the game.
 *
 * @author Nick
 */
public final class GameScreen extends Screen {

    private final PhysicsHandler physicsHandler;

    private final Camera camera;

    private final RandomUtils rand;

    private final Vector2f gridSize;

    private final Grid grid;

    private final List<Entity> entities;
    private final List<Entity> entsToBeRemoved;

    private Sprite pointerSprite;
    private Sprite backgroundSprite;

    private final PlayerEntity player;
    private final int playerHealthIconOffset = 15;

    private int enemiesKilled;
    private int asteroidsBlasted;
    private int shotsFired;

    private final Clock gameClock;
    private Clock pauseClock;
    private float lastTime;
    private float pauseTime;
    
    private Label healthLabel, scoreLabel, posLabel, entityCountLabel;
    
    private Texture bgTexture;

    public GameScreen() {
        super();
        
        physicsHandler = new PhysicsHandler();
        
        camera = new Camera();
        camera.setSize(Window.getSize());
        
        rand = new RandomUtils();
        
        gameClock = new Clock();
        
        gridSize = Vector2f.mul(camera.getSize(), 2);
        
        setBgColor(new Color(150, 150, 150));
        
        grid = new Grid(gridSize);
        
        entities = new LinkedList<>();
        entsToBeRemoved = new LinkedList<>();
        
        player = new PlayerEntity(Vector2f.ZERO);
        entities.add(player);
        camera.setPos(player.getPos());
        
        initSprites();
        initLabels();
        
        bgTexture = new Texture();
    }

    public void initLabels() {
        
        Font font = Precache.getOpenSansFont();
        int fontSize = 20;
        
        // health label
        Text healthText = new Text(Integer.toString(player.getHealth()), font, fontSize);
        healthText.setColor(Color.WHITE);
        healthText.setPosition(player.getPos().x + playerHealthIconOffset, player.getPos().y - playerHealthIconOffset);
        
        healthLabel = new Label(healthText);
        healthLabel.setView(camera.getView());
        
        widgets.add(healthLabel);
        
        // score label
        Text scoreText = new Text(String.format("Score: %s", enemiesKilled), font, fontSize);
        scoreText.setColor(Color.WHITE);
        scoreText.setPosition(5, 5);
        
        scoreLabel = new Label(scoreText);
        widgets.add(scoreLabel);
        
        // position label
        Text posText = new Text(String.format("Postion: (%s, %s)", player.getPos().x, player.getPos().y), font, fontSize);
        posText.setColor(Color.WHITE);
        posText.setPosition(5, 415);
        
        // player position debug text
        posLabel = new Label(posText);
        widgets.add(posLabel);
        
        // entity count label
        Text entityCountText = new Text(String.format("Entity count: %s", entities.size()), font, fontSize);
        entityCountText.setColor(Color.WHITE);
        entityCountText.setPosition(5, 450);

        
        // entity count debug text
        entityCountLabel = new Label(entityCountText);
        widgets.add(entityCountLabel);
    }

    /**
     * initializes the various sprites used by the game screen.
     */
    public void initSprites() {
        Texture pT = Precache.getPointerTexture();
        pointerSprite = new Sprite(pT);
        pointerSprite.setOrigin(Vector2f.div(new Vector2f(pT.getSize()), 2));

        // init background sprite
        Texture bT = Precache.getStarfieldTexture();
        backgroundSprite = new Sprite(bT);
        backgroundSprite.setOrigin(Vector2f.div(new Vector2f(bT.getSize()), 2));
    }

    @Override
    public void draw(RenderTarget rt, RenderStates states) {
        
        camera.handleEdges(this);
        rt.setView(camera.getView());
        rt.clear(getBgColor());
        
        org.jsfml.window.Window rw = (org.jsfml.window.Window)rt;
        
        bgTexture.update(rw);

        // use the custom mouse sprite
        rw.setMouseCursorVisible(false);

        // set delta time
        float currentTime = gameClock.getElapsedTime().asSeconds();
        float dt = currentTime - lastTime;

        // if game was paused, subtract the time paused from dt only on the first
        // frame out of pause
        if (pauseTime != 0) {
            dt -= pauseTime;
            pauseTime = 0;
        }
        
        rt.draw(backgroundSprite);

        // clear grid
        grid.clear();

        checkAndRemoveEntities();

        updateEntitiesAndFillGrid(dt);

        detectEntityCollisionsAndDraw(rt, dt);

        if (Math.random() < 0.01) {
            addEntity(new AsteroidEntity(rand.getRandomEdgeVector(camera), rand.randInt(1, 3)));
        }

        updateAndDrawButtons(rt);

        // set pointer position
        Vector2i mousePos = Mouse.getPosition(rw);
        pointerSprite.setPosition(new Vector2f(mousePos.x, mousePos.y));
        rt.draw(pointerSprite);
        
        rw.display();

        lastTime = currentTime;
    }

    public PhysicsHandler getPhysicsHandler() {
        return physicsHandler;
    }

    private void checkAndRemoveEntities() {

        entsToBeRemoved.clear();

        for (Entity e : entities) {
            if (e.toBeRemoved(grid)) {
                if (e instanceof AsteroidEntity) {
                    asteroidsBlasted++;
                }
                entsToBeRemoved.add(e);
            }
        }

        for (Entity e : entsToBeRemoved) {
            e.handleRemoval(this);
            entities.remove(e);
        }
    }

    private void updateEntitiesAndFillGrid(float dt) {
        for (Entity e : entities) {
            e.update(dt);
            grid.addEntity(e);
        }
    }

    private void detectEntityCollisionsAndDraw(RenderTarget rt, float dt) {
        for (Entity e : entities) {
            e.detectAndHandleCollisions(grid, physicsHandler, dt);
            rt.draw(e);
        }
    }

    private void updateAndDrawButtons(RenderTarget rt) {
        
        Vector2f playerPos = player.getPos();
        // update health text
        healthLabel.setText(String.valueOf(player.getHealth()));
        healthLabel.setPosition(new Vector2f(playerPos.x + playerHealthIconOffset, playerPos.y - playerHealthIconOffset));

        // update score text
        scoreLabel.setText(String.format("Score: %s", asteroidsBlasted));

        // update player position text
        posLabel.setText(String.format("Position: (%.0f, %.0f)", playerPos.x, playerPos.y));

        // update entity count text
        entityCountLabel.setText(String.format("Entity count: %s", entities.size()));
        
        for (Widget w : widgets) {
            rt.draw(w);
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
    
    public Sprite getBGSprite() {
        return new Sprite(bgTexture);
    }
   

    public Vector2f getBounds() {
        return gridSize;
    }

    /**
     * Gets the current player entity on the screen.
     *
     * @return the current screen's player entity.
     */
    public PlayerEntity getCurrentPlayer() {
        return player;
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
     * Adds an entity to the screen.
     *
     * @param e the entity to add.
     */
    public void addEntity(Entity e) {
        if (e instanceof BulletEntity) {
            shotsFired++;
        }
        entities.add(e);
    }

    @Override
    public ScreenName getName() {
        return ScreenName.GAME_SCREEN;
    }
}
