/* Author: Benjamin Fraeyman */
package Loaders;

import javafx.scene.media.AudioClip;
import java.io.File;
import java.io.FileNotFoundException;

public class SoundLoader {
    private static SoundLoader sound;

    private SoundLoader(){}

    static AudioClip crate;
    static AudioClip bomb;
    static AudioClip pistol;
    static AudioClip shotgun;
    static AudioClip rifle;
    static AudioClip launcher;
    static AudioClip reload;
    static AudioClip death;

    public static void loadSounds() throws FileNotFoundException {
        crate = new AudioClip(new File("./src/main/resources/sounds/bonus.wav").toURI().toString());
        bomb = new AudioClip(new File("./src/main/resources/sounds/explosion-small.wav").toURI().toString());
        pistol = new AudioClip(new File("./src/main/resources/sounds/pistol.wav").toURI().toString());
        shotgun = new AudioClip(new File("./src/main/resources/sounds/shotgun.wav").toURI().toString());
        rifle = new AudioClip(new File("./src/main/resources/sounds/rifle.wav").toURI().toString());
        launcher = new AudioClip(new File("./src/main/resources/sounds/launcher.wav").toURI().toString());
        reload = new AudioClip(new File("./src/main/resources/sounds/reload.wav").toURI().toString());
        death = new AudioClip(new File("./src/main/resources/sounds/monster-death.wav").toURI().toString());
    }

    public static SoundLoader getInstance(){
        if (sound == null) {
            sound = new SoundLoader();
        }
        return sound;
    }

    public void play (SpriteLoader.Type type){
        switch (type) {
            case PISTOL:
                pistol.play(25);
                break;

            case SHOTGUN:
                shotgun.play(25);
                break;

            case RIFLE:
                rifle.play(25);
                break;

            case LAUNCHER:
                launcher.play(25);
                break;

            case RELOAD :
                reload.play(25);
                break;

            case DEATH:
                death.play(25);
                break;

            default: break;
        }
    }

    public void play (BonusLoader.Type type){
        switch (type) {
            case CRATE:
                crate.play(25);
                break;

            case BOMB:
                bomb.play(25);
                break;

            default: break;
        }
    }
}