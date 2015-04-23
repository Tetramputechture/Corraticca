package coratticca.action;

import coratticca.entity.gameentity.BulletEntity;
import coratticca.entity.gameentity.PlayerEntity;
import coratticca.screen.GameScreen;
import coratticca.window.Window;
import coratticca.vector.Vector2;
import coratticca.vector.Vector2;

/**
 * Action to create a bullet entity.
 *
 * @author Nick
 */
public class FireAction extends Action {

    private final int fireSpeed;

    private final float playerVelocityScalar;

    public FireAction() {
        fireSpeed = 500;
        playerVelocityScalar = 40;
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
        Vector2 pos = player.getPos().add(new Vector2(sin, cos));
        BulletEntity b = new BulletEntity(pos);

        Vector2 forward = new Vector2(sin, -cos);
        forward = forward.scl(fireSpeed);
        Vector2 inheritedVelocity = player.getVelocity().scl(playerVelocityScalar);
        b.setVelocity(forward.add(inheritedVelocity));

        b.setRotation(playerRotation);
        g.addEntity(b);
    }

    @Override
    public String getName() {
        return "FIRE";
    }
}
