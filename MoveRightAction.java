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
public class MoveRightAction implements Action {
    
    public static final String NAME = "MOVE_RIGHT_ACTION";

    @Override
    public void execute() {
        PlayerEntity.moveRight();
    }
    
    @Override
    public String toString() {
        return NAME;
    }
    
}

