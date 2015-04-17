/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Actions;

import coratticca.Utils.Window;

/**
 *
 * @author Nick
 */
public class ExitGameAction extends Action {

    @Override
    public void execute(Window w) {
        w.getRenderWindow().close();
    }
    
    @Override
    public String getName() {
        return "EXIT_GAME";
    }
}
