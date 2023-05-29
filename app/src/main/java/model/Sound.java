package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.LinkedList;

public class Sound {
    public static boolean soundOff = false;
    private static LinkedList<String> BGM = new LinkedList<String>();

    private static Clip BGMclip;
    private static Float bgmCurVolume = 3.0f;
    private static FloatControl bgmControl;

    private static Clip effetclip;
    private static Float effetCurVolume = 3.0f;
    private static FloatControl effetControl;

    private static int currentIndex = 0;
    private static int frameLength = 0;

    public static void btn() {
        if (!soundOff) {

            effetSound("src/main/resources/button_click.wav");
            effetclip.start();
        }

    }

    public static void warning() {

        if (!soundOff) {

            effetSound("src/main/resources/warning.wav");
            effetclip.start();
        }

    }

    private static void bgMusic(String path) {
        try {
            File f = new File(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);
            // Get a sound clip resource.
            BGMclip = AudioSystem.getClip();

            // Open audio clip and load samples from the audio input stream.
            BGMclip.open(audioIn);
            // Control volume
            bgmControl = (FloatControl) BGMclip.getControl(FloatControl.Type.MASTER_GAIN);
            bgmControl.setValue(Sound.bgmCurVolume);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void effetSound(String path) {
        try {
            File f = new File(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);
            // Get a sound clip resource.
            effetclip = AudioSystem.getClip();

            // Open audio clip and load samples from the audio input stream.

            effetclip.open(audioIn);

            // Control volume
            effetControl = (FloatControl) effetclip.getControl(FloatControl.Type.MASTER_GAIN);
            effetControl.setValue(Sound.effetCurVolume);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void iniListofBGM() {
        BGM.add("src/main/resources/Slideshow.wav");
        BGM.add("src/main/resources/Science.wav");
    }

    public static void playMusic() {
        if (BGMclip == null) {
            iniListofBGM();
        }
        bgMusic(BGM.get(0));
        if (!BGMclip.isRunning()) {
            System.out.println("audio play");
            frameLength = BGMclip.getFrameLength();
            BGMclip.setFramePosition(0);
            BGMclip.start();
        }
        BGMclip.addLineListener(new LineListener() {

            @Override
            public void update(LineEvent event) {
                if (event.getFramePosition() == frameLength) {
                    stopMusic();
                    playNextBgm();
                }

            }

        });

    }

    public static void stopMusic() {
        if (BGMclip.isRunning()) {
            BGMclip.stop();
        }
        BGMclip.close();
    }

    public static void playNextBgm() {
        currentIndex = (currentIndex + 1) % BGM.size();
        bgMusic(BGM.get(currentIndex));
        frameLength = BGMclip.getFramePosition();
        BGMclip.start();
    }

    public static void victory() {
        effetSound("src/main/resources/win.wav");
        effetclip.start();

    }

    public static void fail() {
        effetSound("src/main/resources/echec.wav");
        effetclip.start();
    }

    public static void seteffetVolume(Float v) {
        effetCurVolume = v;
        if (!effetclip.isRunning()) {
            Sound.victory();
        } else {
            effetControl.setValue(v);
        }

    }

    public static void setBGMVolume(Float v) {
        bgmCurVolume = v;
        bgmControl.setValue(v);
    }

}
