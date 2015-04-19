package coratticca.util;

import coratticca.entity.Entity;
import java.util.LinkedList;
import org.jsfml.system.Vector2f;

/**
 * A Grid class to partition the game screen for smoother collision 
 * handling.
 *
 * @author Nick
 */
public class Grid {
    
    // size of the grid
    private final Vector2f gridSize;
    
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
    private final LinkedList<Entity>[][] grid;
    
    private final LinkedList<Entity> retrieveList;
    
    /**
     * Constructs a gridBounds with the specified boundary.
     * @param gridSize the size of the gridBounds.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Grid(Vector2f gridSize) {
        this.gridSize = gridSize;
        gridCellSize = gridSize.x * cellSizeMultipler;
        gridXOffset = (int)((gridSize.x + gridCellSize - 1) / gridCellSize);
        gridYOffset = (int)((gridSize.y + gridCellSize - 1) / gridCellSize);
        
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
        
        retrieveList = new LinkedList<>();
    }
    
    public Vector2f getSize() {
        return gridSize;
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
        LinkedList<Entity> collidables = retrieve(e);
        
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

    private LinkedList<Entity> retrieve(Entity e) {

        retrieveList.clear();
        
        Vector2f ePos = e.getPos();
        
        float topLeftX = Math.max(0, (ePos.x / gridCellSize) + gridXOffset);
        float topLeftY = Math.max(0, (ePos.y / gridCellSize) + gridYOffset);
        
        float edgeLength = e.getBounds().getRadius().x * 2;
        float bottomRightX = Math.min(cols - 1, ((ePos.x + edgeLength - 1) / gridCellSize) + gridXOffset);
        float bottomRightY = Math.min(rows - 1, ((ePos.y + edgeLength - 1) / gridCellSize) + gridYOffset);
        
        for (int x = (int)topLeftX; x <= bottomRightX; x++) {
            for (int y = (int)topLeftY; y <= bottomRightY; y++) {
                LinkedList<Entity> cell = grid[x][y]; 
                for (Entity ent : cell) {
                    if (!retrieveList.contains(ent)) {
                        retrieveList.add(ent);
                    }
                }
            }
        }
        
        // entity should not collide with itself
        retrieveList.remove(e);
        
        return retrieveList;
    }
}
