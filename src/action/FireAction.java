package coratticca.action;

import coratticca.entity.gameentity.BulletEntity;
import coratticca.entity.gameentity.PlayerEntity;
import coratticca.particlesystem.ParticleSystem;
import coratticca.screen.GameScreen;
import coratticca.util.RandomUtils;
import coratticca.window.Window;
import coratticca.vector.Vector2;
import org.jsfml.graphics.Color;

/**
 * Action to create a bullet entity.
 *
 * @author Nick
 */
public class FireAction extends Action {

    private final int fireSpeed;

    private final float playerVelocityScalar;
    
    private ParticleSystem mps;

    public FireAction() {
        fireSpeed = 600;
        playerVelocityScalar = 63;
    }

    @Override
    public void execute() {

        GameScreen g;
        // double check if the current screen is a gamescreen
        if (Window.getCurrentScreen() instanceof GameScreen) {
            g = (GameScreen) Window.getCurrentScreen();
        } else {
            return;
        }

        Window.getAudioHandler().playSound("sounds/firesound.wav", 1);

        PlayerEntity player = g.getCurrentPlayer();
        float playerRotation = player.getRotation();

        float angle = (float) (Math.toRadians(playerRotation));
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        // set position and angle based off current player sprite
        BulletEntity b = new BulletEntity(player.getPosition().add(new Vector2(cos, sin)));
        b.setRotation(playerRotation);

        Vector2 forward = new Vector2(sin, -cos);
        forward = forward.scl(fireSpeed);
        Vector2 inheritedVelocity = player.getVelocity().scl(playerVelocityScalar);
        b.setVelocity(forward.add(inheritedVelocity));
        
        mps = b.getMoveParticleSystem();
        
        mps.setParticleDecayRate(RandomUtils.randInt(15, 20));
        mps.setParticleColor(Color.RED);
        mps.setParticleRadius(1);
        g.addEntity(b);
    }

    @Override
    public String getName() {
        return "FIRE";
    }
}
