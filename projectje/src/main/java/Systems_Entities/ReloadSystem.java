/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.PlayerComponent;
import Components.WeaponComponent;
import Loaders.SoundLoader;
import Loaders.SpriteLoader;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import java.util.Optional;

public class ReloadSystem implements EntitySystem {
    @Inject
    private Engine engine;

    private int counter = 0;

    @Override
    public void update() {
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);
        if (player.get().get(WeaponComponent.class).reloading){
            if (counter == 0){
                SoundLoader.getInstance().play(SpriteLoader.Type.RELOAD);
            }
            counter++;
            if (counter == player.get().get(WeaponComponent.class).reloadspeed){
                player.get().get(WeaponComponent.class).ammo = player.get().get(WeaponComponent.class).ammocapacity;
                player.get().get(WeaponComponent.class).reloading = false;
                counter = 0;
            }
        }
    }
}