package coratticca.screen;

import coratticca.entity.gameentity.AsteroidEntity;
import coratticca.entity.gameentity.BulletEntity;
import coratticca.entity.gameentity.PlayerEntity;
import coratticca.entity.gameentity.GameEntity;
import coratticca.physics.CollisionHandler;
import coratticca.util.RandomUtils;
import coratticca.util.PrecacheUtils;
import coratticca.camera.Camera;
import coratticca.entitygrid.EntityGrid;
import coratticca.util.SpriteUtils;
import coratticca.window.Window;
import coratticca.widget.Label;
import coratticca.widget.Widget;
import java.util.LinkedList;
import java.util.List;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import coratticca.vector.Vector2;
import org.jsfml.window.Mouse;

/**
 * The screen for the game.
 *
 * @author Nick
 */
public final class GameScreen extends Screen {

    private final CollisionHandler physicsHandler;

    private final Camera camera;

    private final Vector2 gridSize;

    private final EntityGrid grid;

    private final List<GameEntity> entities;
    private final List<GameEntity> entsToBeRemoved;

    private Sprite pointerSprite;
    private Sprite backgroundSprite;

    private final PlayerEntity player;

    private int enemiesKilled;
    private int asteroidsBlasted;
    private int shotsFired;

    private final Clock gameClock;
    private Clock pauseClock;
    private float lastTime;
    private float pauseTime;

    private Label scoreLabel, posLabel, entityCountLabel;

    public GameScreen() {
        super();

        physicsHandler = new CollisionHandler();

        camera = new Camera();
        camera.setSize(Window.getSize());

        gameClock = new Clock();

        gridSize = camera.getSize().scl(2);

        setBgColor(new Color(150, 0, 150));

        grid = new EntityGrid(gridSize);

        entities = new LinkedList<>();
        entsToBeRemoved = new LinkedList<>();

        player = new PlayerEntity(Vector2.Zero);
        entities.add(player);

        camera.setPos(player.getPosition());

        initSprites();
        initLabels();
    }

    public void initLabels() {

        Font font = PrecacheUtils.getOpenSansFont();
        int fontSize = 20;

        // score label
        Text scoreText = new Text(String.format("Score: %s", enemiesKilled), font, fontSize);
        scoreText.setColor(Color.WHITE);

        scoreLabel = new Label(scoreText);
        widgets.add(scoreLabel);

        // position label
        Text posText = new Text(String.format("Postion: (%s, %s)", player.getPosition().x, player.getPosition().y), font, fontSize);
        posText.setColor(Color.WHITE);

        posLabel = new Label(posText);
        widgets.add(posLabel);

        // entity count label
        Text entityCountText = new Text(String.format("Entity count: %s", entities.size()), font, fontSize);
        entityCountText.setColor(Color.WHITE);

        entityCountLabel = new Label(entityCountText);
        widgets.add(entityCountLabel);

        updateWidgets(Window.getSize());
    }

    @Override
    public void updateWidgets(Vector2 size) {

        Vector2 playerPos = player.getPosition();

        scoreLabel.setPosition(5, 5);
        scoreLabel.setText(String.format("Score: %s", asteroidsBlasted));

        posLabel.setPosition(5, size.y - 65);
        posLabel.setText(String.format("Position: (%.0f, %.0f)", playerPos.x, playerPos.y));

        entityCountLabel.setPosition(5, size.y - 30);

        entityCountLabel.setText(String.format("Entity count: %s", entities.size()));
    }

    /**
     * initializes the various sprites used by the game screen.
     */
    public void initSprites() {
        Texture pT = PrecacheUtils.getPointerTexture();
        pointerSprite = new Sprite(pT);
        SpriteUtils.setOriginAtCenter(pointerSprite, pT);

        // init background sprite
        Texture bT = PrecacheUtils.getRandomStarfieldTexture(gridSize.scl(2));
        backgroundSprite = new Sprite(bT);
        SpriteUtils.setOriginAtCenter(backgroundSprite, bT);
    }

    @Override
    public void draw(RenderTarget rt, RenderStates states) {

        camera.updateView(this);
        rt.setView(camera.getView());
        rt.clear(getBgColor());

        org.jsfml.window.Window rw = (org.jsfml.window.Window) rt;

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
        
        rt.draw(player);

        if (Math.random() < 0.01) {
            addEntity(new AsteroidEntity(RandomUtils.getRandomEdgeVector(camera), RandomUtils.randInt(1, 3)));
        }

        updateWidgets(Window.getSize());

        for (Widget w : widgets) {
            rt.draw(w);
        }

        // set pointer position
        Vector2 mousePos = new Vector2(Mouse.getPosition(rw));
        pointerSprite.setPosition(mousePos.toVector2f());
        rt.draw(pointerSprite);

        rw.display();

        lastTime = currentTime;
    }

    public CollisionHandler getPhysicsHandler() {
        return physicsHandler;
    }

    private void checkAndRemoveEntities() {

        entsToBeRemoved.clear();


        for (GameEntity e : entities) {
            if (e.toBeRemoved(grid)) {
                if (e instanceof AsteroidEntity) {
                    asteroidsBlasted++;
                }
                entsToBeRemoved.add(e);
            }
        }

        for (GameEntity e : entsToBeRemoved) {
            e.handleRemoval(this);
            entities.remove(e);
        }
    }

    private void updateEntitiesAndFillGrid(float dt) {
        for (GameEntity e : entities) {
            e.update(dt);
            grid.addEntity(e);
        }
    }

    private void detectEntityCollisionsAndDraw(RenderTarget rt, float dt) {
        for (GameEntity e : entities) {
            e.detectAndHandleCollisions(grid, physicsHandler, dt);
            rt.draw(e);
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

    public Vector2 getBounds() {
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
    public void addEntity(GameEntity e) {
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
