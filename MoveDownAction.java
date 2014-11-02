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
public class MoveDownAction implements Action {
    
    public static final String NAME = "MOVE_DOWN";

    @Override
    public void execute() {
        PlayerEntity.moveDown();
    }
    
    @Override
    public String toString() {
        return NAME;
    }
}