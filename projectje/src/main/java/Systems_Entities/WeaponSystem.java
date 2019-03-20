/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.PlayerComponent;
import Components.WeaponComponent;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import Loaders.SpriteLoader;

public class WeaponSystem implements EntitySystem {
    @Inject
    private Engine engine;

    @Override
    public void update() {
        for (Entity player : engine.getEntitiesWithComponents(PlayerComponent.class)) {
            switch (player.get(WeaponComponent.class).weaponType) {
                case "pistol":
                    player.get(WeaponComponent.class).pellets = 1;
                    player.get(WeaponComponent.class).spread = 1;
                    player.get(WeaponComponent.class).ammocapacity = 12;
                    player.get(WeaponComponent.class).reloadspeed = 30;
                    player.get(WeaponComponent.class).weapon = SpriteLoader.Type.PISTOL;
                    player.get(WeaponComponent.class).ammo = player.get(WeaponComponent.class).ammocapacity;
                    break;
                case "shotgun":
                    player.get(WeaponComponent.class).pellets = 5;
                    player.get(WeaponComponent.class).spread = 10;
                    player.get(WeaponComponent.class).ammocapacity = 6;
                    player.get(WeaponComponent.class).reloadspeed = 120;
                    player.get(WeaponComponent.class).weapon = SpriteLoader.Type.SHOTGUN;
                    player.get(WeaponComponent.class).ammo = player.get(WeaponComponent.class).ammocapacity;
                    break;
                case "rifle":
                    player.get(WeaponComponent.class).pellets = 1;
                    player.get(WeaponComponent.class).spread = 3;
                    player.get(WeaponComponent.class).ammocapacity = 30;
                    player.get(WeaponComponent.class).reloadspeed = 30;
                    player.get(WeaponComponent.class).weapon = SpriteLoader.Type.RIFLE;
                    player.get(WeaponComponent.class).ammo = player.get(WeaponComponent.class).ammocapacity;
                    break;

                case "launcher":
                    player.get(WeaponComponent.class).pellets = 1;
                    player.get(WeaponComponent.class).spread = 1;
                    player.get(WeaponComponent.class).ammocapacity = 5;
                    player.get(WeaponComponent.class).reloadspeed = 120;
                    player.get(WeaponComponent.class).weapon = SpriteLoader.Type.LAUNCHER;
                    player.get(WeaponComponent.class).ammo = player.get(WeaponComponent.class).ammocapacity;
                    break;
            }
            engine.unregisterSystem(this);
        }
    }
}