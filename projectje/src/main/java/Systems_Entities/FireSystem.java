/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.PlayerComponent;
import Loaders.SpriteLoader;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import java.util.Optional;

public class FireSystem implements EntitySystem {
    @Inject
    private Engine engine;

    private int counter = 0;

    @Override
    public void update() {
        SpriteLoader.getInstance().fire = true;
        if (counter == 0){
            Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);
            player.get().get(PlayerComponent.class).damageMultiplier += 2;
        }
        counter++;
        if (counter == 500){
            Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);
            player.get().get(PlayerComponent.class).damageMultiplier -= 2;
            SpriteLoader.getInstance().fire = false;
            engine.unregisterSystem(this);
        }
    }
}