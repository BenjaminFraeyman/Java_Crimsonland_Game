/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.DifficultyComponent;
import Components.EnemyComponent;
import Components.PlayerComponent;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import java.util.Optional;

public class FrozenSystem implements EntitySystem {
    @Inject
    private Engine engine;

    private int count = 0;
    @Override
    public void update() {
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);
        player.get().get(DifficultyComponent.class).cEnemies = player.get().get(DifficultyComponent.class).enemies;
        count++;
        for(Entity enemy : engine.getEntitiesWithComponents(EnemyComponent.class)){
            enemy.get(EnemyComponent.class).speed = 0;
        }
        if (count == 500){
            player.get().get(DifficultyComponent.class).cEnemies = 0;
            for(Entity enemy : engine.getEntitiesWithComponents(EnemyComponent.class)){
                enemy.get(EnemyComponent.class).speed = 1;
                player.get().get(DifficultyComponent.class).cEnemies++;
            }
            engine.unregisterSystem(this);
        }
    }
}