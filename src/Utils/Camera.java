/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import coratticca.Utils.Screen.GameScreen;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

/**
 * A Camera class that handles the window view and scrolling.
 * @author Nick
 */
public class Camera {
    
    private static final View cam = new View();
    
    private static Vector2f camPos = GameScreen.getCurrentPlayer().getPos();
    
    static {
        cam.setSize(Window.getSize().x, Window.getSize().y);
        handleEdges();
    }
    
    /**
     * Handles when the view of the camera is near an edge.
     * When the player is near an edge, the camera stops following the player.
     */
    public static void handleEdges() {
        Vector2f playerPos = GameScreen.getCurrentPlayer().getPos();
        
        float gWidth = GameScreen.getBounds().x;
        float gHeight = GameScreen.getBounds().y;
        
        float cHalfWidth = cam.getSize().x / 2;
        float cHalfHeight = cam.getSize().y / 2;
        
        if (playerPos.x > -gWidth + cHalfWidth && 
                playerPos.x < gWidth - cHalfWidth) {
            camPos = new Vector2f(GameScreen.getCurrentPlayer().getPos().x, camPos.y);
        } 
        if (playerPos.y > -gHeight + cHalfHeight && 
                playerPos.y < gHeight - cHalfHeight) {
            camPos = new Vector2f(camPos.x, GameScreen.getCurrentPlayer().getPos().y);
        } 
        cam.setCenter(camPos);
    }
    
    /**
     * Gets the View of the camera.
     * @return the camera's View.
     */
    public static View getView() {
        return cam;
    }
}
