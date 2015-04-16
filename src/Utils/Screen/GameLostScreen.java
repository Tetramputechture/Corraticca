/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils.Screen;

import coratticca.Utils.Button;
import coratticca.Actions.ChangeToGameScreenAction;
import coratticca.Actions.ChangeToMainMenuScreenAction;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;

/**
 *
 * @author Nick
 */
public class GameLostScreen extends Screen {
    
    public GameLostScreen(GameScreen g) {
        
        super(g.getWindow());
        
        super.setBgColor(Color.BLACK);
        
        // cursor must be visible
        super.window.getRenderWindow().setMouseCursorVisible(true);
        
        // add game lost text
        buttons.add(new Button( super.window,
                                new Vector2f(super.window.getSize().x/2,
                                super.window.getSize().y/2 - 50),
                                48,
                                "Game Lost! :(",
                                "fonts/OpenSans-Regular.ttf",
                                Color.WHITE,
                                null));
        
        // add exit button
        buttons.add(new Button( window,
                                new Vector2f(super.window.getSize().x/2, 
                                super.window.getSize().y/2 + 50),
                                24, 
                                "Exit to Main Menu", 
                                "fonts/OpenSans-Regular.ttf", 
                                Color.WHITE,
                                new ChangeToMainMenuScreenAction()));
        
        // add new game button
        buttons.add(new Button( window,
                                new Vector2f(super.window.getSize().x/2,
                                super.window.getSize().y/2 + 100),
                                24,
                                "New Game",
                                "fonts/OpenSans-Regular.ttf",
                                Color.WHITE,
                                new ChangeToGameScreenAction()));
        
        // add stats text
        buttons.add(new Button( window,
                                new Vector2f(super.window.getSize().x - 100,
                                super.window.getSize().y/2 + 200),
                                18,
                                String.format("Asteroids blasted: %s\n"
                                        + "Shots fired: %s\n"
                                        + "Accuracy: %.4f", 
                                        g.getAsteroidsBlasted(), 
                                        g.getShotsFired(), 
                                        g.getAccuracy()),
                                "fonts/OpenSans-Regular.ttf",
                                Color.WHITE,
                                null));
    }

    @Override
    public void show() {
        
        super.window.getRenderWindow().clear(super.getBgColor());
        
        for (Button i : buttons) {
            i.draw();
        }

        super.window.getRenderWindow().display();
    }
    
    @Override
    public ScreenName getName() {
        return ScreenName.GAME_LOST_SCREEN;
    }
}
