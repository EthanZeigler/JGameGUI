/*
 * Copyright (c) 2016 under the Attribution-ShareAlike 4.0 International licence.
 * See JGameGUI-licence.txt for more information
 */

package com.ethanzeigler.jgamegui.sound;

import javax.sound.sampled.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by ethanzeigler on 4/27/16.
 * Wrapper for Java's {@link Clip} to simplify things. Represents an playable audio file as well as a playback
 * controller for that file. Accepts .wav files.
 */
public class AudioClip {
    private Clip clip;
    private boolean isPaused = false;
    /**
     * Represents if the file IO is open
     */
    private boolean isOpen = true;

    /**
     * Opens a new wav file of the given path. This operation is thread-locking (will pause code until done)
     * and resource intensive.
     * @param fileName name of the file (with path if necessary) to open
     */
    public AudioClip(String fileName) {
        URL url = AudioClip.class.getResource("/" + fileName);
        if(url == null)
            throw new RuntimeException(new FileNotFoundException("File could not be found. If your audio file is inside " +
                    "of a folder, it may not be once it is compiled. Try entering the file name directly."));
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
            DataLine.Info info = new DataLine.Info(Clip.class, inputStream.getFormat());
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(inputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            System.out.println("File type is unsupported. Convert your file to a \".wav\"");
            isOpen = false;
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
            isOpen = false;
        }
    }

    /**
     * Attempts to play the AudioClip one time through.
     * <p>Failures can be caused by the clip currently being busy {@link Clip#isActive()}</p>
     * @return true if the clip was started, false if start failed.
     */
    public boolean play() {
        //System.out.println("Is busy: " + (!isOpen || clip.isActive() || clip.isRunning()));
        if(!isOpen || clip.isActive() || clip.isRunning())
            return false; // clip is currently busy

        setPosition();
        clip.start();
        return true;
    }

    /**
     * Attempts to play the AudioClip continuously.
     * <p>Failures can be caused by the clip currently being busy {@link Clip#isActive()}</p>
     * @return true if the clip was started, false if start failed.
     */
    public boolean loop() {
        if (!isOpen || clip.isActive() || clip.isRunning())
            return false; // clip is currently busy

        setPosition();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        return true;
    }

    /**
     * Plays the file the amount of times specified.
     */
    public boolean loop(int numOfLoops) {
        if (!isOpen || clip.isActive() || clip.isRunning())
            return false; // clip is currently busy

        setPosition();
        clip.loop(numOfLoops - 1);
        return true;
    }

    /**
     * Stops the current AudioClip's playback. The data buffer will be flushed.
     * (The clip will go back to the beginning on the next playback)
     */
    public void stop() {
        clip.stop();
        clip.flush();
        isPaused = false;
    }

    /**
     * Pauses the current AudioClip's playback. If it is not playing, the request is ignored.
     * <i>There is not a 100% guarantee that the clip will resume from the spot it was paused.</i>
     */
    public void pause() {
        if((clip.isRunning() || clip.isActive()) && isOpen) {
            clip.stop();
            isPaused = true;
        }
    }

    /**
     * Closes the file I/O to the AudioClip. The clip is hereby considered dead.
     */
    public void dispose() {
        clip.close();
        isOpen = false;
    }

    /**
     * Sets the start position of the clip before playing
     */
    private void setPosition() {
        if(!isPaused) {
            clip.setFramePosition(0);
        }
        isPaused = false;
    }
}
