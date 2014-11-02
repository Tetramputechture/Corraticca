/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.util.List;

/**
 *
 * @author Nick
 */
public interface Screen {
    
    void show();
    
    public List<Button> getButtons();
    
    @Override
    public String toString();

    
}
