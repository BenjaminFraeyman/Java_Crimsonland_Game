/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.SoundComponent;
import Loaders.SoundLoader;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;

public class SoundSystem implements EntitySystem {
    @Inject
    private Engine engine;

    @Override
    public void update() {
        for(Entity entity :engine.getEntitiesWithComponents(SoundComponent.class)){
            SoundLoader.getInstance().play(entity.get(SoundComponent.class).sound);
            entity.remove(SoundComponent.class);
        }
    }
}