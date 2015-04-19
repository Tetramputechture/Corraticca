package coratticca.util.screen;

import coratticca.entity.AsteroidEntity;
import coratticca.entity.BulletEntity;
import coratticca.entity.PlayerEntity;
import coratticca.entity.Entity;
import coratticca.util.CPhysics;
import coratticca.util.CRandom;
import coratticca.util.CPrecache;
import coratticca.util.Camera;
import coratticca.util.Grid;
import coratticca.util.Window;
import coratticca.util.widget.CWidget;
import java.util.LinkedList;
import java.util.List;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
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
    
    private final Window window;

    public GameScreen(Window w) {
        super(Color.BLACK);
        
        this.window = w;
        
        physicsHandler = new CPhysics();
        
        camera = new Camera(window);
        
        rand = new CRandom();
        
        gameClock = new Clock();
        
        gridSize = Vector2f.mul(window.getSize(), 2);
        
        setBgColor(new Color(150, 150, 150));
        
        grid = new Grid(gridSize);
        
        entities = new LinkedList<>();
        entsToBeRemoved = new LinkedList<>();
        
        player = new PlayerEntity(window, Vector2f.ZERO);
        entities.add(player);
        camera.setPos(player.getPos());
        
        initSprites();
        initButtons();
    }
    
    public Window getWindow() {
        return window;
    }

    public void initButtons() {
        
        Font font = CPrecache.getOpenSansFont();
        int fontSize = 20;
        
        Text healthText = new Text(Integer.toString(player.getHealth()), font, fontSize);
        healthText.setColor(Color.WHITE);
        
        Text scoreText = new Text(String.format("Score: %s", enemiesKilled), font, fontSize);
        scoreText.setColor(Color.WHITE);
        
        Text posText = new Text(String.format("Postion: (%s, %s)", player.getPos().x, player.getPos().y), font, fontSize);
        posText.setColor(Color.WHITE);
        
        Text entityCountText = new Text(String.format("Entity count: %s", entities.size()), font, fontSize);
        entityCountText.setColor(Color.WHITE);
        
        // health text
        CWidget healthWidget = new CWidget(new Vector2f((int) player.getPos().x + playerHealthIconOffset,
                        (int) player.getPos().y - playerHealthIconOffset),
                healthText,
                camera.getView());
        widgets.add(healthWidget);

        // score text
        CWidget scoreWidget = new CWidget(new Vector2f(5, 5),
                scoreText,
                null);
        widgets.add(scoreWidget);

        // player position debug text
        CWidget posWidget = new CWidget(new Vector2f(5, 415),
                posText,
                null);
        widgets.add(posWidget);
        
        // entity count debug text
        CWidget entityCountWidget = new CWidget(new Vector2f(5, 450),
                entityCountText,
                null);
        widgets.add(entityCountWidget);
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
    public void draw(RenderTarget rt, RenderStates states) {
        
        camera.handleEdges(this);
        rt.setView(camera.getView());
        rt.clear(getBgColor());

        // use the custom mouse sprite
        ((org.jsfml.window.Window)rt).setMouseCursorVisible(false);

        // set delta time
        float currentTime = gameClock.getElapsedTime().asSeconds();
        float dt = currentTime - lastTime;

        // if game was paused, subtract the time paused from dt only on the first
        // frame out of pause
        if (pauseTime != 0) {
            dt -= pauseTime;
            pauseTime = 0;
        }
        
        backgroundSprite.draw(rt, states);

        // clear grid
        grid.clear();

        checkAndRemoveEntities();

        updateEntitiesAndFillGrid(dt);

        detectEntityCollisionsAndDraw(dt);

        if (Math.random() < 0.01) {
            addEntity(new AsteroidEntity(rand.getRandomEdgeVector(camera), rand.randInt(1, 3)));
        }

        updateAndDrawButtons(rt, states);

        // set pointer position
        pointerSprite.setPosition(window.getInputHandler().getMousePos());
        pointerSprite.draw(rt, states);
        
        ((org.jsfml.window.Window)rt).display();

        lastTime = currentTime;
    }

    public CPhysics getPhysicsHandler() {
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

    private void detectEntityCollisionsAndDraw(float dt) {
        for (Entity e : entities) {
            e.detectAndHandleCollisions(grid, physicsHandler, dt);
            window.getRenderWindow().draw(e);
        }
    }

    private void updateAndDrawButtons(RenderTarget rt, RenderStates states) {
        
        Vector2f playerPos = player.getPos();
        // update health text
        widgets.get(0).setTextString(String.valueOf(player.getHealth()));
        widgets.get(0).setPosition(new Vector2f(playerPos.x + playerHealthIconOffset, playerPos.y - playerHealthIconOffset));

        // update score text
        widgets.get(1).setTextString(String.format("Score: %s", asteroidsBlasted));

        // update player position text
        widgets.get(2).setTextString(String.format("Position: (%.0f, %.0f)", playerPos.x, playerPos.y));

        // update entity count text
        widgets.get(3).setTextString(String.format("Entity count: %s", entities.size()));
        
        for (CWidget w : widgets) {
            w.draw(rt, states);
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

    public Image getBGImage() {
        return window.getRenderWindow().capture();
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
