package coratticca.util;

import coratticca.util.screen.GameScreen;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

/**
 * A Camera class that handles the window view and scrolling.
 * @author Nick
 */
public class Camera {
    
    private final View view;
    
    private Vector2f pos;
    
    public Camera() {
        view = new View();
    }
    
    public void setSize(Vector2f size) {
        view.setSize(size);
    }
    
    public Vector2f getSize() {
        return view.getSize();
    }
    
    /**
     * Updates this Camera's view.
     * When the player is near an edge, the camera stops following the player.
     * @param game the game that specifies the edges
     */
    public void updateView(GameScreen game) {
        Vector2f playerPos = game.getCurrentPlayer().getPos();
        
        Vector2f gameBounds = game.getBounds();
        
        Vector2f viewSize = view.getSize();
        
        float gWidth = gameBounds.x;
        float gHeight = gameBounds.y;
        
        float cHalfWidth = viewSize.x / 2;
        float cHalfHeight = viewSize.y / 2;
        
        if (playerPos.x > -gWidth + cHalfWidth && 
                playerPos.x < gWidth - cHalfWidth) {
            pos = new Vector2f(playerPos.x, pos.y);
        } 
        if (playerPos.y > -gHeight + cHalfHeight && 
                playerPos.y < gHeight - cHalfHeight) {
            pos = new Vector2f(pos.x, playerPos.y);
        } 
        view.setCenter(pos);
    }
       
    public void setPos(Vector2f pos) {
        this.pos = pos;
    }
    
    public Vector2f getPos() {
        return pos;
    }
    
    public FloatRect getBounds() {
        Vector2f wSize = view.getSize();
        return new FloatRect(pos.x - wSize.x/2f-100, pos.y - wSize.y/2f-100,
                                wSize.x + 200, wSize.y + 200);
    }
    
    /**
     * Gets the View of the camera.
     * @return the camera's View.
     */
    public View getView() {
        return view;
    }
}
