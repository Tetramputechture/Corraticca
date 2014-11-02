/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.util.List;

/**
 * A Screen interface to handle all Screens.
 * @author Nick
 */
public interface Screen {
    
    /**
     * Shows the screen.
     */
    void show();
    
    /**
     * Gets the buttons of the screen.
     * @return a List containing the screen's buttons.
     */
    public List<Button> getButtons();
    
    /**
     * Gets the String representation of the Screen.
     * @return the String representation of the Screen.
     */
    @Override
    public String toString();

    
}
