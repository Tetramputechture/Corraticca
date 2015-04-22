package coratticca.entity;

import coratticca.util.PhysicsHandler;
import coratticca.util.SpriteUtils;
import coratticca.util.Precache;
import coratticca.util.VectorUtils;
import coratticca.util.Grid;
import coratticca.util.screen.GameScreen;
import coratticca.util.screen.GameLostScreen;
import coratticca.util.Window;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;

/**
 * Entity representing the player.
 * @author Nick
 */
public final class PlayerEntity extends Entity {
    
    private double angle;
    
    private final float maxMoveSpeed;
    private Vector2f target;
    
    private final float accelRate;
    private final float fConst;
    
    private final int maxHealth;
    private int currentHealth;
    
    public PlayerEntity(Vector2f pos) {
        super(pos);
        initSprite();
        
        velocity = Vector2f.ZERO;
               
        // set movement variables
        maxMoveSpeed = 120;
        accelRate = 20;
        fConst = 0.95f;
        maxHealth = 3;
        currentHealth = maxHealth;
    }
    
    /**
     * Updates the player entity based on frametime.
     * @param dt the difference in time since last frame.
     */
    @Override
    public void update(float dt) {
        
        // handle key presses
        int ttx = (Keyboard.isKeyPressed(Keyboard.Key.A) ? -1 : 0) + (Keyboard.isKeyPressed(Keyboard.Key.D) ? 1 : 0);
	int tty = (Keyboard.isKeyPressed(Keyboard.Key.W) ? -1 : 0) + (Keyboard.isKeyPressed(Keyboard.Key.S) ? 1 : 0);
        target = new Vector2f(ttx, tty);

        // normalize target direction
        target = VectorUtils.normalize(target);

        // set length to target velocity
        // increasing accelRate should make movements more sharp and dramatic
        target = Vector2f.mul(target, accelRate);

        // target is now acceleration
        Vector2f acc = target;
        
        // integrate acceleration to get velocity
        velocity = Vector2f.add(velocity, Vector2f.mul(acc, dt));

        // limit velocity vector to maxMoveSpeed
        float speed = VectorUtils.length(velocity);
        if (speed > maxMoveSpeed) {
            velocity = Vector2f.div(velocity, speed);
            velocity = Vector2f.mul(velocity, maxMoveSpeed);
        }

        // set velocity
        pos = Vector2f.add(pos, velocity);
        
        // apply friction
        velocity = Vector2f.mul(velocity, fConst);
        
        sprite.setPosition(pos);
        
        // set player angle based on mouse position
        Vector2i mousePos = Mouse.getPosition(Window.getRenderWindow());
        Vector2i truePos = Window.getRenderWindow().mapCoordsToPixel(pos);
        angle = Math.atan2( mousePos.y - truePos.y, 
                            mousePos.x - truePos.x);
        
        angle *= (180/Math.PI);
        if(angle < 0) {
            angle += 360;
        }
        
        sprite.setRotation(90 + (float)angle);
    }
    
    @Override
    public void initSprite() {
        Texture t = Precache.getPlayerTexture();
        Sprite s = new Sprite(t);
        SpriteUtils.setOriginAtCenter(s, t);
        
        sprite = s;
        sprite.setPosition(pos);
    }
    
    @Override
    public void detectAndHandleCollisions(Grid grid, PhysicsHandler physics, float dt) {
        if (isOutOfBounds(grid)) {
            handleOutOfBounds(grid);
        }
    }
    
    @Override
    public boolean isOutOfBounds(Grid grid) {
        float gWidth = grid.getSize().x;
        float gHeight = grid.getSize().y;
        
        float pAdjustedWidth = sprite.getLocalBounds().width * .9f;
        float pAdjustedHeight = sprite.getLocalBounds().height * .9f;
        
        return (pos.x > gWidth - pAdjustedWidth ||
                pos.x < -gWidth + pAdjustedWidth ||
                pos.y > gHeight - pAdjustedHeight ||
                pos.y < -gHeight + pAdjustedHeight);
    }
    
    @Override
    public void handleOutOfBounds(Grid grid) {
        float tx = -1;
        float ty = -1;
        
        float gWidth = grid.getSize().x;
        float gHeight = grid.getSize().y;
        
        float pAdjustedWidth = sprite.getLocalBounds().width * .9f;
        float pAdjustedHeight = sprite.getLocalBounds().height * .9f;
        
        if (pos.x > gWidth - pAdjustedWidth) {
            tx = gWidth - pAdjustedWidth;
        } else if (pos.x < -gWidth + pAdjustedWidth) {
            tx = -gWidth + pAdjustedWidth;
        }
        if (pos.y > gHeight - pAdjustedHeight) {
            ty = gHeight - pAdjustedHeight;
        } else if (pos.y < -gHeight + pAdjustedHeight) {
            ty = -gHeight + pAdjustedHeight;
        }
        
        if (tx != -1) {
            pos = new Vector2f(tx, pos.y);
        }
        if (ty != -1) {
            pos = new Vector2f(pos.x, ty);
        }
    }
    
    @Override
    public boolean toBeRemoved(Grid grid) {
        return currentHealth <= 0;
    }
    
    @Override
    public void handleRemoval(GameScreen g) {
        Window.setCurrentScreen(new GameLostScreen(g));
    }
    
    /**
     * Changes the player's health.
     * @param r the health amount to be changed by.
     */
    public void changeHealth(int r) {
        Color c = sprite.getColor();
        sprite.setColor(new Color(c.r, c.g, c.b, c.a - 100));
        currentHealth += r;
    }
    
    /**
     * Gets the player's current health.
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