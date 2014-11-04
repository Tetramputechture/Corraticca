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
public class EnemyWave {
    
    private final int spawnRate;
    
    private final int maxEnemies;
    private int numEnemies;
    
    public EnemyWave(int spawnRate, int maxEnemies) {
        this.spawnRate = spawnRate;
        this.maxEnemies = maxEnemies;
        numEnemies = 0;
    }
    
    public void start() {
        if (numEnemies != maxEnemies) {
            new SpawnEnemyAction().execute();
            numEnemies++;
        } 
    }
}
