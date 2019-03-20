/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.*;
import Factories.BonusFactory;
import Factories.EnemyFactory;
import Loaders.BonusLoader;
import Loaders.SoundLoader;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class SpawnerSystem implements EntitySystem {
    @Inject
    private Engine engine;

    @Override
    public void update() {
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);

        if (player.get().get(DifficultyComponent.class).cEnemies < player.get().get(DifficultyComponent.class).enemies && player.get().get(PlayerComponent.class).hp != 0){
            Entity enemy2  = new EnemyFactory().makeEnemy(player.get().get(DifficultyComponent.class).enemyType);
            int randomX = ThreadLocalRandom.current().nextInt( 0, 3);
            int randomY = ThreadLocalRandom.current().nextInt( 0, 3);
            while (randomX == randomY && randomY == 2){
                randomX = ThreadLocalRandom.current().nextInt( 0, 3);
                randomY = ThreadLocalRandom.current().nextInt( 0, 3);
            }
            switch (randomX){
                case 0:
                    randomX = (int)player.get().get(PositionComponent.class).xPosition - 1000;
                    break;
                case 1:
                    randomX = (int)player.get().get(PositionComponent.class).xPosition + 1000;
                    break;
                case 2:
                    randomX = (int)player.get().get(PositionComponent.class).xPosition;
                    break;
            }
            switch (randomY){
                case 0:
                    randomY = (int)player.get().get(PositionComponent.class).yPosition - 1000;
                    break;
                case 1:
                    randomY = (int)player.get().get(PositionComponent.class).yPosition + 1000;
                    break;
                case 2:
                    randomY = (int)player.get().get(PositionComponent.class).yPosition;
                    break;
            }
            enemy2.add(new PositionComponent(randomX,randomY));
            enemy2.add(new HitBoxComponent(enemy2.get(PositionComponent.class).xPosition - enemy2.get(TextureComponent.class).image.getWidth()/2,
                                           enemy2.get(PositionComponent.class).yPosition - enemy2.get(TextureComponent.class).image.getHeight()/2,
                                              enemy2.get(TextureComponent.class).image.getWidth(), enemy2.get(TextureComponent.class).image.getHeight()));
            engine.add(enemy2);
            player.get().get(DifficultyComponent.class).cEnemies = player.get().get(DifficultyComponent.class).cEnemies + 1;
        }

        if (player.get().get(PlayerComponent.class).hp != 0){
            double randomX = ThreadLocalRandom.current().nextInt(0, 1000);
            if (randomX == 500){
                Entity lootcrate = new BonusFactory().makeBonus("type3");
                SoundLoader.getInstance().play(BonusLoader.Type.CRATE);
                double X = player.get().get(PositionComponent.class).xPosition;
                double Y = player.get().get(PositionComponent.class).yPosition - 250;
                lootcrate.add(new PositionComponent(X,Y));
                BonusLoader bonuss = BonusLoader.getInstance();
                lootcrate.add(new TextureComponent(bonuss.getTexture(BonusLoader.Type.CRATE)));
                lootcrate.add(new HitBoxComponent(X - bonuss.getTexture(BonusLoader.Type.CRATE).getWidth()/2, Y -bonuss.getTexture(BonusLoader.Type.CRATE).getHeight()/2, bonuss.getTexture(BonusLoader.Type.CRATE).getWidth(), bonuss.getTexture(BonusLoader.Type.CRATE).getHeight()));
                engine.add(lootcrate);
            }
        }
    }
}