package coratticca.entity.gameentity;

import coratticca.entity.Entity;
import coratticca.physics.CollisionHandler;
import coratticca.util.SpriteUtils;
import coratticca.util.PrecacheUtils;
import coratticca.entitygrid.EntityGrid;
import coratticca.screen.GameScreen;
import coratticca.screen.GameLostScreen;
import coratticca.window.Window;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import coratticca.vector.Vector2;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;

/**
 * Entity representing the player.
 *
 * @author Nick
 */
public final class PlayerEntity extends GameEntity {

    private double angle;

    private final float maxMoveSpeed;
    private Vector2 target;

    private final float accelRate;
    private final float fConst;

    private final int maxHealth;
    private int currentHealth;

    public PlayerEntity(Vector2 pos) {
        super(pos);
        initSprite();

        velocity = Vector2.Zero;

        // set movement variables
        maxMoveSpeed = 120;
        accelRate = 20;
        fConst = 0.95f;
        maxHealth = 3;
        currentHealth = maxHealth;
    }

    /**
     * Updates the player entity based on frametime.
     *
     * @param dt the difference in time since last frame.
     */
    @Override
    public void update(float dt) {

        // handle key presses
        int ttx = (Keyboard.isKeyPressed(Keyboard.Key.A) ? -1 : 0) + (Keyboard.isKeyPressed(Keyboard.Key.D) ? 1 : 0);
        int tty = (Keyboard.isKeyPressed(Keyboard.Key.W) ? -1 : 0) + (Keyboard.isKeyPressed(Keyboard.Key.S) ? 1 : 0);
        target = new Vector2(ttx, tty);

        // normalize target direction
        target = target.normalize();

        // set length to target velocity
        // increasing accelRate should make movements more sharp and dramatic
        target = target.scl(accelRate);

        // target is now acceleration
        Vector2 acc = target;

        // integrate acceleration to get velocity
        velocity = velocity.sclAdd(acc, dt);

        // limit velocity vector to maxMoveSpeed
        velocity = velocity.clamp(0, maxMoveSpeed);

        // add velocity to position
        position = position.add(velocity);

        // apply friction
        velocity = velocity.scl(fConst);

        sprite.setPosition(position.toVector2f());

        // set player angle based on mouse position
        Vector2 mousePos = new Vector2(Mouse.getPosition(Window.getRenderWindow()));
        Vector2 truePos = new Vector2(Window.getRenderWindow().mapCoordsToPixel(position.toVector2f()));
        angle = Math.atan2(mousePos.y - truePos.y, mousePos.x - truePos.x);

        angle *= (180 / Math.PI);
        if (angle < 0) {
            angle += 360;
        }

        sprite.setRotation(90 + (float) angle);
    }

    @Override
    public void initSprite() {
        Texture t = PrecacheUtils.getPlayerTexture();
        Sprite s = new Sprite(t);
        SpriteUtils.setOriginAtCenter(s, t);

        sprite = s;
        sprite.setPosition(position.toVector2f());
    }

    @Override
    public void detectAndHandleCollisions(EntityGrid grid, CollisionHandler physics, float dt) {
        if (isOutOfBounds(grid)) {
            handleOutOfBounds(grid);
        }
    }

    @Override
    public boolean isOutOfBounds(EntityGrid grid) {
        float gWidth = grid.getSize().x;
        float gHeight = grid.getSize().y;

        float pAdjustedWidth = sprite.getLocalBounds().width * .9f;
        float pAdjustedHeight = sprite.getLocalBounds().height * .9f;

        return (position.x > gWidth - pAdjustedWidth
                || position.x < -gWidth + pAdjustedWidth
                || position.y > gHeight - pAdjustedHeight
                || position.y < -gHeight + pAdjustedHeight);
    }

    @Override
    public void handleOutOfBounds(EntityGrid grid) {
        float tx = -1;
        float ty = -1;

        float gWidth = grid.getSize().x;
        float gHeight = grid.getSize().y;

        float pAdjustedWidth = sprite.getLocalBounds().width * .9f;
        float pAdjustedHeight = sprite.getLocalBounds().height * .9f;

        if (position.x > gWidth - pAdjustedWidth) {
            tx = gWidth - pAdjustedWidth;
        } else if (position.x < -gWidth + pAdjustedWidth) {
            tx = -gWidth + pAdjustedWidth;
        }
        if (position.y > gHeight - pAdjustedHeight) {
            ty = gHeight - pAdjustedHeight;
        } else if (position.y < -gHeight + pAdjustedHeight) {
            ty = -gHeight + pAdjustedHeight;
        }

        if (tx != -1) {
            position = new Vector2(tx, position.y);
        }
        if (ty != -1) {
            position = new Vector2(position.x, ty);
        }
    }

    @Override
    public boolean toBeRemoved(EntityGrid grid) {
        return currentHealth <= 0;
    }

    @Override
    public void handleRemoval(GameScreen g) {
        Window.setCurrentScreen(new GameLostScreen(g));
    }

    /**
     * Changes the player's health.
     *
     * @param r the health amount to be changed by.
     */
    public void changeHealth(int r) {
        Color c = sprite.getColor();
        sprite.setColor(new Color(c.r, c.g, c.b, c.a - 100));
        currentHealth += r;
    }

    /**
     * Gets the player's current health.
     *
     * @return the health of the player.
     */
    public int getHealth() {
        return currentHealth;
    }

    @Override
    public String toString() {
        return "Player";
    }
}
