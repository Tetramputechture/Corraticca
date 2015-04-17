/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import coratticca.Utils.Screen.GameScreen;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

/**
 * A Camera class that handles the window view and scrolling.
 * @author Nick
 */
public class Camera {
    
    private final Window window;
    
    private final View view;
    
    private Vector2f pos;
    
    public Camera(Window window) {
        this.window = window;
        view = new View();
        view.setSize(window.getSize());
    }
    
    public void setPos(Vector2f pos) {
        this.pos = pos;
    }
    
    /**
     * Handles when the view of the camera is near an edge.
     * When the player is near an edge, the camera stops following the player.
     * @param game the game that specifies the edges
     */
    public void handleEdges(GameScreen game) {
        
        if (game.getCurrentPlayer() == null) {
            return;
        }
        
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
    
    public Vector2f camPos() {
        return pos;
    }
    
    public FloatRect getBounds() {
        Vector2f wSize = window.getSize();
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
