/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import coratticca.Entities.Entity;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import org.jsfml.system.Vector2f;

/**
 * A Grid class to partition the game screen for smoother collision 
 * handling.
 *
 * @author Nick
 */
public class Grid {
    
    // size of each cell in the grid
    private final float gridCellSize;
    
    // rows and columns in the grid
    private final int rows;
    private final int cols;
    
    // size of cells in relation to grid
    // must be below 1
    private final float cellSizeMultipler = 1/32f;
    
    // offsets to handle negative position coordinates in grid
    private final int gridXOffset;
    private final int gridYOffset;

    // data
    private final List<Entity>[][] grid;
    
    private final List<Entity> retrieveList = new LinkedList<>();
    
    /**
     * Constructs a gridBounds with the specified boundary.
     * @param gridBounds the size of the gridBounds.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Grid(Vector2f gridBounds) {
        gridCellSize = gridBounds.x * cellSizeMultipler;
        gridXOffset = (int)((gridBounds.x + gridCellSize - 1) / gridCellSize);
        gridYOffset = (int)((gridBounds.y + gridCellSize - 1) / gridCellSize);
        
        // need enough columns and rows
        // so multiply the offset by 2 to account for negative values
        cols = gridXOffset * 2;
        rows = gridYOffset * 2;
        
        grid = new LinkedList[cols][rows];
        
        // initialize gridBounds indices
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                grid[c][r] = new LinkedList<>();
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
        
        float topLeftX = Math.max(0, (ePos.x / gridCellSize) + gridXOffset);
        float topLeftY = Math.max(0, (ePos.y / gridCellSize) + gridYOffset);
        
        float edgeLength = e.getBounds().getRadius().x * 2;
        float bottomRightX = Math.min(cols - 1, ((ePos.x + edgeLength - 1) / gridCellSize) + gridXOffset);
        float bottomRightY = Math.min(rows - 1, ((ePos.y + edgeLength - 1) / gridCellSize) + gridYOffset);
        
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
        
        float topLeftX = Math.max(0, (ePos.x / gridCellSize) + gridXOffset);
        float topLeftY = Math.max(0, (ePos.y / gridCellSize) + gridYOffset);
        
        float edgeLength = e.getBounds().getRadius().x * 2;
        float bottomRightX = Math.min(cols - 1, ((ePos.x + edgeLength - 1) / gridCellSize) + gridXOffset);
        float bottomRightY = Math.min(rows - 1, ((ePos.y + edgeLength - 1) / gridCellSize) + gridYOffset);
        
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
