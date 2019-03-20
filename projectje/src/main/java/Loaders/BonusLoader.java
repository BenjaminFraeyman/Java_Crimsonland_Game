/* Author: Benjamin Fraeyman */
package Loaders;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BonusLoader {
    public enum Type { PISTOL, SHOTGUN, RIFLE, CRATE, HEALTH, SPEED, AMMO, DAMAGE, FREEZE, FIRE, SHIELD, BOMB }

    private static BonusLoader bonus;
    private static Image pistol;
    private static Image shotgun;
    private static Image rifle;
    private static Image crate;
    private static Image hp;
    private static Image speed;
    private static Image ammo;
    private static Image damage;
    private static Image freeze;
    private static Image fire;
    private static Image shield;
    private static Image bomb;

    private BonusLoader(){}

    public static BonusLoader getInstance(){
        if (bonus == null) {
            bonus = new BonusLoader();
        }
        return bonus;
    }

    public static void loadImages() throws FileNotFoundException {
        pistol = new Image(new FileInputStream("./src/main/resources/sprites/handgun.png"));
        shotgun = new Image(new FileInputStream("./src/main/resources/sprites/shotgun.png"));
        rifle = new Image(new FileInputStream("./src/main/resources/sprites/rifle.png"));
        crate = new Image(new FileInputStream("./src/main/resources/sprites/crate.png"));
        hp = new Image(new FileInputStream("./src/main/resources/sprites/hp-pickup2.png"));
        speed = new Image(new FileInputStream("./src/main/resources/sprites/speed-upgrade2.png"));
        ammo = new Image(new FileInputStream("./src/main/resources/sprites/ammo-upgrade2.png"));
        damage = new Image(new FileInputStream("./src/main/resources/sprites/damage-upgrade2.png"));
        freeze = new Image(new FileInputStream("./src/main/resources/sprites/freeze-pickup2.png"));
        fire = new Image(new FileInputStream("./src/main/resources/sprites/fire-pickup2.png"));
        shield = new Image(new FileInputStream("./src/main/resources/sprites/shield-pickup2.png"));
        bomb = new Image(new FileInputStream("./src/main/resources/sprites/bomb-pickup2.png"));
    }

    public Image getTexture (Type type){
        switch (type) {
            case PISTOL:
                return this.pistol;

            case SHOTGUN:
                return this.shotgun;

            case RIFLE:
                return this.rifle;

            case CRATE:
                return this.crate;

            case HEALTH:
                return this.hp;

            case SPEED:
                return this.speed;

            case AMMO:
                return this.ammo;

            case DAMAGE:
                return this.damage;

            case FREEZE:
                return this.freeze;

            case FIRE:
                return this.fire;

            case SHIELD:
                return this.shield;

            case BOMB:
                return this.bomb;

            default:
                return this.rifle;
        }
    }
}