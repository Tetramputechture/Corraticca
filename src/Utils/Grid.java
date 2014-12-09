/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import coratticca.Entities.Entity;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import org.jsfml.system.Vector2f;

/**
 * A Grid class to partition the game screen for smoother collision 
 * handling.
 *
 * @author Nick
 */
public class Grid {
    
    private final float gridSize;
    
    // size of each cell in the grid
    private final float gridCellSize;
    
    // rows and columns in the grid
    private final int rows;
    private final int cols;

    // data
    private final List<Entity>[][] grid;
    
    private final List<Entity> retrieveList = new ArrayList<>();
    
    /**
     * Constructs a gridBounds with the specified boundary.
     * @param gridBounds the size of the gridBounds.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Grid(Vector2f gridBounds) {
        this.gridSize = gridBounds.x;
        gridCellSize = gridBounds.x / 32;
        cols = (int)((gridBounds.x + gridCellSize - 1) / gridCellSize) + 32;
        rows = (int)((gridBounds.y + gridCellSize - 1) / gridCellSize) + 24;;
        grid = new ArrayList[cols][rows];
        
        // initialize gridBounds indices
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                grid[c][r] = new ArrayList<>();
            }
        }
    }

    public void clear() {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                grid[x][y].clear();
            }
        }
    }

    public void addEntity(Entity e) {
        
        // get all the points the entity takes up
        // do not accept values outside of the grid
        
        Vector2f ePos = e.getPos();
        
        float topLeftX = Math.max(0, (ePos.x / gridCellSize) + 32);
        float topLeftY = Math.max(0, (ePos.y / gridCellSize) + 24);
        
        float edgeLength = e.getBounds().getRadius().x * 2;
        float bottomRightX = Math.min(cols - 1, ((ePos.x + edgeLength - 1) / gridCellSize) + 32);
        float bottomRightY = Math.min(rows - 1, ((ePos.y + edgeLength - 1) / gridCellSize) + 24);
        
        for (int x = (int) topLeftX; x <= bottomRightX; x++) {
            for (int y = (int) topLeftY; y <= bottomRightY; y++) {
                grid[x][y].add(e);
            }
        }    
    }
    
    public Entity getNearest(Entity e) {
        Entity nearest = null;
        float maxDist = Float.MAX_VALUE;
        List<Entity> collidables = retrieve(e);
        
        for (Entity toCheck : collidables) {
            Vector2f diff = Vector2f.sub(toCheck.getPos(), e.getPos());
            float dist = CVector.length(diff);
            if (dist < maxDist) {
                nearest = toCheck;
                maxDist = dist;
            }
        }
        return nearest;
    }

    private List<Entity> retrieve(Entity e) {

        retrieveList.clear();
        
        Vector2f ePos = e.getPos();
        
        float topLeftX = Math.max(0, (ePos.x / gridCellSize) + 32);
        float topLeftY = Math.max(0, (ePos.y / gridCellSize) + 24);
        
        float edgeLength = e.getBounds().getRadius().x * 2;
        float bottomRightX = Math.min(cols - 1, ((ePos.x + edgeLength - 1) / gridCellSize) + 32);
        float bottomRightY = Math.min(rows - 1, ((ePos.y + edgeLength - 1) / gridCellSize) + 24);
        
        for (int x = (int)topLeftX; x <= bottomRightX; x++) {
            for (int y = (int)topLeftY; y <= bottomRightY; y++) {
                List<Entity> cell = grid[x][y]; 
                for (Entity ent : cell) {
                    if (!retrieveList.contains(ent)) {
                        retrieveList.add(ent);
                    }
                }
            }
        }
        
        // entity should not collide with itself
        retrieveList.remove(e);
        
        return Collections.unmodifiableList(retrieveList);
    }
}
