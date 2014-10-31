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
public class UseAction implements Action {
    
    public static final String NAME = "USE_ACTION";

    @Override
    public void execute() {
        System.out.println("Use key pressed!");
    }
    
    @Override
    public String toString() {
        return "USE_ACTION";
    }
    
}
