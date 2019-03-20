/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.PlayerComponent;
import Components.ShieldComponent;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import java.util.Optional;

public class ShieldSystem implements EntitySystem {
    @Inject
    private Engine engine;
    private int count = 0;
    @Override
    public void update() {
        count++;
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);
        player.get().get(ShieldComponent.class).active = true;
        if (count == 500){
            player.get().get(ShieldComponent.class).active = false;
            engine.unregisterSystem(this);
        }
    }
}