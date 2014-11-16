/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coratticca.Utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;

/**
 * Handles all game sounds.
 * @author Nick
 */
public class Audio {
    
    /**
     * Plays a sound with specified filename and pitch
     * @param filename the filename of the sound to be played
     * @param pitch the pitch of the played sound
     */
    public static void playSound(String filename, float pitch) {
        
        SoundBuffer buffer = new SoundBuffer();
        
        try {
            buffer.loadFromFile(Paths.get(filename));
        } catch (IOException ex) {
            Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, 
                    String.format("File %s not found!", filename), ex);
        }
        
        Sound sound = new Sound(buffer);
        
        sound.setPitch(pitch);
        
        sound.play();
    }
    
    
}
