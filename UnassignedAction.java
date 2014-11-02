/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

/**
 * An unassigned action (used for buttons with only text)
 * @author Nick
 */
public class UnassignedAction implements Action {
    
    /**
     * The name of the action.
     */
    public static final String NAME = "UNASSIGNED_ACTION";
    
    /**
     * Does nothing.
     */
    @Override
    public void execute() {
    }
    
    @Override
    public String toString() {
        return "UNASSIGNED_ACTION";
    }
    
}
