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
    
    private final View cam;
    
    private Vector2f pos;
    
    public Camera(Window window) {
        this.window = window;
        cam = new View();
        cam.setSize(window.getSize().x, window.getSize().y);
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
        
        float gWidth = game.getBounds().x;
        float gHeight = game.getBounds().y;
        
        float cHalfWidth = cam.getSize().x / 2;
        float cHalfHeight = cam.getSize().y / 2;
        
        if (playerPos.x > -gWidth + cHalfWidth && 
                playerPos.x < gWidth - cHalfWidth) {
            pos = new Vector2f(playerPos.x, pos.y);
        } 
        if (playerPos.y > -gHeight + cHalfHeight && 
                playerPos.y < gHeight - cHalfHeight) {
            pos = new Vector2f(pos.x, playerPos.y);
        } 
        cam.setCenter(pos);
    }
    
    public Vector2f camPos() {
        return pos;
    }
    
    public FloatRect getBounds() {
        return new FloatRect(pos.x - window.getSize().x/2f-100, pos.y - window.getSize().y/2f-100,
                                window.getSize().x + 200, window.getSize().y + 200);
    }
    
    /**
     * Gets the View of the camera.
     * @return the camera's View.
     */
    public View getView() {
        return cam;
    }
}
