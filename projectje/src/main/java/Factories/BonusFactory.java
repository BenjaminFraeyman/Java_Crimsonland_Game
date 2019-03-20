/* Author: Benjamin Fraeyman */
package Factories;

import Components.BonusComponent;
import Components.ConditionalComponent;
import org.ibcn.gso.esf.Entity;
import Loaders.BonusLoader;

import java.util.concurrent.ThreadLocalRandom;

public class BonusFactory {
    public Entity makeBonus(String type){
        Entity bs = new Entity();
        BonusComponent bonusSettings;
        if(type.equalsIgnoreCase("type1")){
            bs.add(new ConditionalComponent());
            int random = ThreadLocalRandom.current().nextInt(0, 4);
            switch (random){
                case 0:
                    bonusSettings = new BonusComponent();
                    bonusSettings.type = "weapon";
                    bonusSettings.name = "pistol";
                    bs.add(bonusSettings);
                    return bs;

                case 1:
                    bonusSettings = new BonusComponent();
                    bonusSettings.type = "weapon";
                    bonusSettings.name = "shotgun";
                    bs.add(bonusSettings);
                    return bs;

                case 2:
                    bonusSettings = new BonusComponent();
                    bonusSettings.type = "weapon";
                    bonusSettings.name = "rifle";
                    bs.add(bonusSettings);
                    return bs;

                case 3:
                    bonusSettings = new BonusComponent();
                    bonusSettings.type = "weapon";
                    bonusSettings.name = "launcher";
                    bs.add(bonusSettings);
                    return bs;
            }
        } else if(type.equalsIgnoreCase("type2")){
            bonusSettings = new BonusComponent();
            bonusSettings.type = "levelcrate";
            bonusSettings.opened = false;
            int random = ThreadLocalRandom.current().nextInt(0, 4);
            switch (random){
                case 0:
                    bonusSettings.name = "Health";
                    bs.add(bonusSettings);
                    return bs;

                case 1:
                    bonusSettings.name = "Ammo";
                    bs.add(bonusSettings);
                    return bs;

                case 2:
                    bonusSettings.name = "Damage";
                    bs.add(bonusSettings);
                    return bs;

                case 3:
                    bonusSettings.name = "Speed";
                    bs.add(bonusSettings);
                    return bs;
            }
        }else if(type.equalsIgnoreCase("type3")){
            bonusSettings = new BonusComponent();
            bonusSettings.type = "bonuscrate";
            bonusSettings.opened = false;
            int random = ThreadLocalRandom.current().nextInt(0, 4);
            switch (random){
                case 0:
                    bonusSettings.name = "Freeze";
                    bs.add(bonusSettings);
                    return bs;

                case 1:
                    bonusSettings.name = "Fire";
                    bs.add(bonusSettings);
                    return bs;

                case 2:
                    bonusSettings.name = "Shield";
                    bs.add(bonusSettings);
                    return bs;

                case 3:
                    bonusSettings.name = "Bomb";
                    bs.add(bonusSettings);
                    return bs;
            }
        }
        return null;
    }
}