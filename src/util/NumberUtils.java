/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.util;

/**
 *
 * @author Nick
 */
public class NumberUtils {
    
    public static int round(float n) {
        return (int) (n + 0.5f);
    }
    
    public static float roundTo3(float n) {
        return (long) (n * 1000 + 0.5) / 1000.0f;
    }
    
}
