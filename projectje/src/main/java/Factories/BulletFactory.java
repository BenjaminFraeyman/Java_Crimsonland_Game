/* Author: Benjamin Fraeyman */
package Factories;

import Components.BulletComponent;
import Components.PositionComponent;
import Components.TextureComponent;
import Components.TransformComponent;
import org.ibcn.gso.esf.Entity;
import Loaders.SpriteLoader;

public class BulletFactory {
    private  final SpriteLoader spro = SpriteLoader.getInstance();

    public Entity makeBullet(String weaponType, TransformComponent trnsfrm){
        Entity bullet = new Entity();
        bullet.add(new PositionComponent(500, 500));
        BulletComponent bulletSettings;
        if(weaponType.equalsIgnoreCase("pistol")){
            bulletSettings = new BulletComponent("pistol");
            bulletSettings.range = 1000;
            bulletSettings.damage = 15;
            bullet.add(bulletSettings);
            bullet.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.BULLET1)));
            TransformComponent trans = trnsfrm;
            bullet.add(trans);
            return bullet;
        } else if(weaponType.equalsIgnoreCase("shotgun")){
            bulletSettings = new BulletComponent("shotgun");
            bulletSettings.range = 500;
            bulletSettings.damage = 15;
            bullet.add(bulletSettings);
            bullet.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.BULLET1)));
            bullet.add(trnsfrm);
            return bullet;
        }else if(weaponType.equalsIgnoreCase("rifle")){
            bulletSettings = new BulletComponent("rifle");
            bulletSettings.range = 1000;
            bulletSettings.damage = 30;
            bullet.add(bulletSettings);
            bullet.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.BULLET1)));
            bullet.add(trnsfrm);
            return bullet;
        }else if(weaponType.equalsIgnoreCase("launcher")){
            bulletSettings = new BulletComponent("launcher");
            bulletSettings.range = 750;
            bulletSettings.damage = 250;
            bulletSettings.radius = 350;
            bullet.add(bulletSettings);
            bullet.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.BULLET1)));
            bullet.add(trnsfrm);
            return bullet;
        }else if(weaponType.equalsIgnoreCase("bomb")){
            bulletSettings = new BulletComponent("launcher");
            bulletSettings.range = 0;
            bulletSettings.damage = 250;
            bulletSettings.detonate = true;
            bulletSettings.radius = 1000;
            bullet.add(bulletSettings);
            bullet.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.BULLET1)));
            bullet.add(trnsfrm);
            return bullet;
        }
        else {return null;}
    }

    public Entity makeBomb(PositionComponent pos, TransformComponent trans){
        Entity bullet = new Entity();
        bullet.add(pos);
        BulletComponent bulletSettings;
        bulletSettings = new BulletComponent("launcher");
        bulletSettings.range = 0;
        bulletSettings.damage = 250;
        bulletSettings.detonate = true;
        bulletSettings.radius = 500;
        bullet.add(bulletSettings);
        bullet.add(trans);
        bullet.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.BULLET1)));
        return bullet;
    }
}