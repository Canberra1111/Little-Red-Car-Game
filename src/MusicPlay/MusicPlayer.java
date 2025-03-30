package src.MusicPlay;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Music player class, this class to achieve the background music rolling playback.
 * Volume adjustment function
 * @author Yuan Jiayi
 * @since 1.0
 */

public class MusicPlayer implements Runnable {
    private final File soundFile;
    private Thread thread;
    private volatile boolean isPlaying; // used to control the playback status
    private final boolean isCirculate;
    private float volume;

    /**
     * represents a constructor for a music player
     * @param filepath Path for storing music files
     * @param isCirculate Whether the music plays in a loop
     */
    public MusicPlayer(String filepath, boolean isCirculate)  {
        this.isCirculate = isCirculate;
        soundFile = new File(filepath);

        this.volume = 0.5f;
    }

    /**
     This method represents the run() method of a music player.
     It continuously plays audio from a specified file in a loop.
     @author Yuan Jiayi
     */
    @Override
    public void run() {
        byte[] musicBuffer = new byte[1024 * 128];
        isPlaying = true; // set the playback status to true
        do {
            AudioInputStream audioInputStream;
            SourceDataLine sourceDataLine = null;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                AudioFormat format = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
                sourceDataLine.open(format);
                sourceDataLine.start();
                int byteCount = 0;
                while (byteCount != -1 && isPlaying) { // Check the playback status
                    byteCount = audioInputStream.read(musicBuffer, 0, musicBuffer.length);
                    if (byteCount >= 0) {
                        adjustVolume(musicBuffer, byteCount);
                        sourceDataLine.write(musicBuffer, 0, byteCount);
                    }
                }
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            } finally {
                if (sourceDataLine != null) {
                    sourceDataLine.drain();
                    sourceDataLine.close();
                }
            }
        } while (isCirculate && isPlaying);
    }


    /**
     * This method initiates a new thread and starts playing music using the run() method.
     */
    public void playMusic() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * This method stops the music playback by interrupting the thread responsible for playing it.
     */
    public void stopMusic() {
        isPlaying = false; // set the playback status to false
    }

    /**
     * This method set the volume
     * @param volume it is a float value that display the loudness of the background music
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * This method implements the how to adjust the volume
     * @param buffer It is a byte type array that is used to store input audio data. Traversing the
     *  buffer array, the audio data is gradually enlarged or reduced based on the volume coefficient,
     *  thereby modifying the volume
     * @param byteCount It represents the number of bytes to be processed.
     */
    private void adjustVolume(byte[] buffer, int byteCount) {
        for (int i = 0; i < byteCount; i += 2) {
            short sample = (short) ((buffer[i + 1] << 8) | (buffer[i] & 0xff));
            sample = (short) (sample * volume);
            buffer[i] = (byte) (sample & 0xff);
            buffer[i + 1] = (byte) ((sample >> 8) & 0xff);
        }
    }
}