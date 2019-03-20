/* Author: Benjamin Fraeyman */
package Loaders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

public class SpriteLoader {
    public enum Type { WORLD,  PLAYER, MOVINGPLAYER, CROSSHAIR, BULLET1, EA, BLOOD, ACTIVISION, PISTOL, SHOTGUN, RIFLE, INVESTORS, ICE, LAUNCHER, ANLAUNCHER, DEATH, RELOAD }
    public boolean fire = false;
    private static SpriteLoader sprite;

    private static Image level1;
    private static HashMap<String, Image> playerMap = new LinkedHashMap<>();
    private static Image crosshair;
    private static Image bullet1;
    private static HashMap<String, Image> eaMap = new LinkedHashMap<>();
    private static Image BLOOD;
    private static HashMap<String, Image> acMap = new LinkedHashMap<>();
    private static Image pistol;
    private static Image shotgun;
    private static Image rifle;
    private static HashMap<String, Image> investorMap = new LinkedHashMap<>();
    private static Image ice;
    private static Image launcher;
    private static ArrayList<Image> anlauncher = new ArrayList();
    private static Image bullet2;
    private static Image death;

    private SpriteLoader(){}

    private static HashMap<String, Image> loadFiles(String pathName, HashMap<String, Image> imageMap){
        File dir = new File(pathName);
        for (final File f : dir.listFiles()) {
            try {
                BufferedImage img = ImageIO.read(f);
                Image image = SwingFXUtils.toFXImage(img, null );
                imageMap.put(f.getName(), image);
            } catch (final IOException e) {
            }
        }
        return imageMap;
    }

    private static ArrayList<Image> loadFiles(String pathName, ArrayList<Image> imageMap){
        File dir = new File(pathName);
        for (final File f : dir.listFiles()) {
            try {
                BufferedImage img = ImageIO.read(f);
                imageMap.add(SwingFXUtils.toFXImage(img, null ));
            } catch (final IOException e) {
            }
        }
        return imageMap;
    }

    public static SpriteLoader getInstance(){
        if (sprite == null) {
            sprite = new SpriteLoader();
        }
        return sprite;
    }
    public static void loadImages() throws FileNotFoundException{
        level1 = new Image(new FileInputStream("./src/main/resources/sprites/level1.png"));
        playerMap = loadFiles("./src/main/resources/sprites/player", playerMap);
        crosshair = new Image(new FileInputStream("./src/main/resources/sprites/crosshair.png"));
        bullet1 = new Image(new FileInputStream("./src/main/resources/sprites/bullet/bullet3.png"));
        eaMap = loadFiles("./src/main/resources/sprites/monsters/2", eaMap);
        BLOOD = new Image(new FileInputStream("./src/main/resources/sprites/blood.png"));
        acMap = loadFiles("./src/main/resources/sprites/monsters/1", acMap);
        pistol = new Image(new FileInputStream("./src/main/resources/sprites/handgun.png"));
        shotgun = new Image(new FileInputStream("./src/main/resources/sprites/shotgun.png"));
        rifle = new Image(new FileInputStream("./src/main/resources/sprites/rifle.png"));
        investorMap = loadFiles("./src/main/resources/sprites/monsters/3", investorMap);
        ice = new Image(new FileInputStream("./src/main/resources/sprites/ice.png"));
        launcher = new Image(new FileInputStream("./src/main/resources/sprites/grenade_launcher.png"));
        anlauncher = loadFiles("./src/main/resources/sprites/explosions/small", anlauncher);
        bullet2 = new Image(new FileInputStream("./src/main/resources/sprites/firebullet.png"));
        death = new Image(new FileInputStream("./src/main/resources/sprites/enemy.png"));
    }

    private int CurrentPlayer = 21;
    private Iterator<Map.Entry<String, Image>> it_pl = playerMap.entrySet().iterator();
    private Map.Entry<String, Image> currentPlayer;

    private int CurrentEA = 21;
    private Iterator<Map.Entry<String, Image>> it_ea = eaMap.entrySet().iterator();
    private Map.Entry<String, Image> currentEA;

    private int CurrentActivision = 21;
    private Iterator<Map.Entry<String, Image>> it_ac = acMap.entrySet().iterator();
    private Map.Entry<String, Image> currentActivision;

    private int CurrentInv = 21;
    private Iterator<Map.Entry<String, Image>> it_in = investorMap.entrySet().iterator();
    private Map.Entry<String, Image> currentInv;

    public Image getTexture (Type type){
        switch (type) {
            case WORLD:
                return this.level1;

            case PLAYER:
                return playerMap.entrySet().stream().findFirst().get().getValue();

            case MOVINGPLAYER:
                CurrentPlayer++;
                if (CurrentPlayer > 20){
                    if (!it_pl.hasNext()) {
                        it_pl = playerMap.entrySet().iterator();
                    }
                    currentPlayer = it_pl.next();
                    CurrentPlayer = 0;
                }
                return currentPlayer.getValue();

            case CROSSHAIR:
                return this.crosshair;

            case BULLET1:
                if (!fire){
                    return this.bullet1;
                } else return this.bullet2;


            case EA:
                CurrentEA++;
                if (CurrentEA > 20){
                    if (!it_ea.hasNext()) {
                        it_ea = eaMap.entrySet().iterator();
                    }
                    currentEA = it_ea.next();
                    CurrentEA = 0;
                }
                return currentEA.getValue();

            case BLOOD:
                return this.BLOOD;

            case ACTIVISION:
                CurrentActivision++;
                if (CurrentActivision > 20){
                    if (!it_ac.hasNext()) {
                        it_ac = acMap.entrySet().iterator();
                    }
                    currentActivision = it_ac.next();
                    CurrentActivision = 0;
                }
                return currentActivision.getValue();

            case PISTOL:
                return this.pistol;

            case SHOTGUN:
                return this.shotgun;

            case RIFLE:
                return this.rifle;

            case INVESTORS:
                CurrentInv++;
                if (CurrentInv > 20){
                    if (!it_in.hasNext()) {
                        it_in = investorMap.entrySet().iterator();
                    }
                    currentInv = it_in.next();
                    CurrentInv = 0;
                }
                return currentInv.getValue();

            case ICE:
                return this.ice;

            case LAUNCHER:
                return this.launcher;

            case DEATH:
                return this.death;

            default:
                return this.level1;
        }
    }

    public Image getTexture (Type type, int test){
        switch (type) {
            case ANLAUNCHER:
                return anlauncher.get(test);

            default: return anlauncher.get(test);
        }
    }
}