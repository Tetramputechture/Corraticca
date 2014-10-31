/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

/**
 *
 * @author Nick
 */
public class UnassignedAction implements Action {
    
    public static final String NAME = "UNASSIGNED_ACTION";
    
    @Override
    public void execute() {
    }
    
    @Override
    public String toString() {
        return "UNASSIGNED_ACTION";
    }
    
}
