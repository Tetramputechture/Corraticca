/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;

/**
 *
 * @author Nick
 */
public class GameScreen implements Screen {

    private static final Color bgColor;

    private static final int numButtons;

    private static final Button[] buttons;
    
    private static final List<Entity> entities;
    
    private static int numEntities;
    
    private static final Sprite pointerSprite;
    
    // private static Level currentLevel;
    
    private static final Clock clock;
    private static float lastTime;

    static {
        numButtons = 0;
        buttons = new Button[numButtons];
        bgColor = Color.WHITE;
        entities = new ArrayList<>();
        
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
    public Button[] getButtons() {
        return buttons;
    }
    

    public static Color getBGColor() {
        return bgColor;
    }
    
    public static void addEntity(Entity e) {
        entities.add(e);
        numEntities++;
        System.out.format("Entity count: %s\n", numEntities);
    }

    @Override
    public String toString() {
        return "GAME";
    }
}
