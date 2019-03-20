/* Author: Benjamin Fraeyman */
package Factories;

import Components.EnemyComponent;
import Components.TextureComponent;
import org.ibcn.gso.esf.Entity;
import Loaders.SpriteLoader;

public class EnemyFactory {
    private  final SpriteLoader spro = SpriteLoader.getInstance();

    public Entity makeEnemy(String enemyType){
        Entity enemy = new Entity();
        EnemyComponent enemySettings;
        if(enemyType.equalsIgnoreCase("Activision")){
            enemySettings = new EnemyComponent("Activision");
            enemySettings.hp = 35;
            enemySettings.damage = 8;
            enemySettings.xpReward = 50;
            enemySettings.speed = 2.5;
            enemySettings.type = SpriteLoader.Type.ACTIVISION;
            enemy.add(enemySettings);
            enemy.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.ACTIVISION)));
            return enemy;
        } else if(enemyType.equalsIgnoreCase("EA")){
            enemySettings = new EnemyComponent("EA");
            enemySettings.hp = 160;
            enemySettings.damage = 16;
            enemySettings.xpReward = 500;
            enemySettings.speed = 1.5;
            enemySettings.type = SpriteLoader.Type.EA;
            enemy.add(enemySettings);
            enemy.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.EA)));
            return enemy;
        }  else if(enemyType.equalsIgnoreCase("Investors")){
            enemySettings = new EnemyComponent("Investors");
            enemySettings.hp = 350;
            enemySettings.damage = 38;
            enemySettings.xpReward = 50000;
            enemySettings.speed = 0.75;
            enemySettings.type = SpriteLoader.Type.INVESTORS;
            enemy.add(enemySettings);
            enemy.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.INVESTORS)));
            return enemy;
        }
        return null;
    }
}