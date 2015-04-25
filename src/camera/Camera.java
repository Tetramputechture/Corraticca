package coratticca.camera;

import coratticca.screen.GameScreen;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.View;
import coratticca.vector.Vector2;

/**
 * A Camera class that handles the window view and scrolling.
 *
 * @author Nick
 */
public class Camera {

    private final View view;

    private Vector2 pos;

    public Camera() {
        view = new View();
    }

    public void setSize(Vector2 size) {
        view.setSize(size.toVector2f());
    }

    public Vector2 getSize() {
        return new Vector2(view.getSize());
    }

    /**
     * Updates this Camera's view. When the player is near an edge, the camera
     * stops following the player.
     *
     * @param game the game that specifies the edges
     */
    public void updateView(GameScreen game) {
        Vector2 playerPos = game.getCurrentPlayer().getPosition();

        Vector2 gameBounds = game.getBounds();

        Vector2 viewSize = getSize();

        float gWidth = gameBounds.x;
        float gHeight = gameBounds.y;

        float cHalfWidth = viewSize.x / 2;
        float cHalfHeight = viewSize.y / 2;

        if (playerPos.x > -gWidth + cHalfWidth
                && playerPos.x < gWidth - cHalfWidth) {
            pos = new Vector2(playerPos.x, pos.y);
        }
        if (playerPos.y > -gHeight + cHalfHeight
                && playerPos.y < gHeight - cHalfHeight) {
            pos = new Vector2(pos.x, playerPos.y);
        }
        view.setCenter(pos.toVector2f());
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getPos() {
        return pos;
    }

    public FloatRect getBounds() {
        Vector2 wSize = getSize();
        return new FloatRect(pos.x - wSize.x / 2f - 100, pos.y - wSize.y / 2f - 100,
                wSize.x + 200, wSize.y + 200);
    }

    /**
     * Gets the View of the camera.
     *
     * @return the camera's View.
     */
    public View getView() {
        return view;
    }
}
