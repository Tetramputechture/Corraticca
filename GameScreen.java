/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;

/**
 * @author Nick
 * The screen for the game.
 */
public final class GameScreen implements Screen {

    private static final Color bgColor;

    private final List<Button> buttons;
    
    private static final List<Entity> entities;
    
    private final Sprite pointerSprite;
    
    // private static Level currentLevel;
    
    private final Clock clock;
    private float lastTime;
    
    static {
        entities = new ArrayList<>();
        bgColor = Color.WHITE;
    }

    public GameScreen(boolean resetGame) {
        
        buttons = new ArrayList<>();
        
        // clears entities (including Player) if game is reset
        if (resetGame) {
            entities.clear();
        }
       
        // init mouse sprite
        Texture pointerTexture = new Texture();
        try {
            pointerTexture.loadFromFile(Paths.get("pointer.png"));
        } catch (IOException ex) {
            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        pointerSprite = new Sprite(pointerTexture);
        
        // all game screens have a player
        Entity playerEntity = new PlayerEntity(PlayerEntity.getSprite());
        addEntity(playerEntity);
        
        clock = new Clock();
        lastTime = 0;
    }

    @Override
    public void show() {

        Window.getWindow().clear(bgColor);
        
        // use the custom mouse sprite
        Window.getWindow().setMouseCursorVisible(false);

        float currentTime = clock.getElapsedTime().asSeconds();
        float dt = currentTime - lastTime;
        
        for (Entity e : entities) {
            e.draw();
            e.update(dt);
        }
        
        pointerSprite.setPosition(Input.getMousePos());
        
        Window.getWindow().draw(pointerSprite);
        Window.getWindow().display();
        
        lastTime = currentTime;
    }

    @Override
    public List<Button> getButtons() {
        return Collections.unmodifiableList(buttons);
    }
    

    public static Color getBGColor() {
        return bgColor;
    }
    
    public static void addEntity(Entity e) {
        entities.add(e);
        System.out.format("Entity count: %s\n", entities.size());
    }

    @Override
    public String toString() {
        return "GAME";
    }
}
