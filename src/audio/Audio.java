package coratticca.audio;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;

/**
 * Handles all game sounds.
 *
 * @author Nick
 */
public class Audio {

    private final Sound sound;

    public Audio() {
        sound = new Sound();
    }

    /**
     * Plays a sound with specified filename and pitch.
     *
     * @param filename the filename of the sound to be played
     * @param pitch the pitch of the played sound
     */
    public void playSound(String filename, float pitch) {

        SoundBuffer sBuffer = new SoundBuffer();

        try {
            sBuffer.loadFromFile(Paths.get(filename));
        } catch (IOException ex) {
            Logger.getLogger(Audio.class.getName()).log(Level.SEVERE,
                    String.format("File %s not found!", filename), ex);
        }

        sound.setBuffer(sBuffer);

        sound.setPitch(pitch);

        sound.play();
    }
}
