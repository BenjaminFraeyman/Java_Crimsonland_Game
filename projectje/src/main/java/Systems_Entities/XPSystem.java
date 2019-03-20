/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.*;
import Factories.BonusFactory;
import Loaders.BonusLoader;
import Loaders.SoundLoader;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import java.util.Optional;

public class XPSystem implements EntitySystem {
    @Inject
    private Engine engine;

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    @Override
    public void update() {
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);

        if(player.get().get(PlayerComponent.class).score >= player.get().get(PlayerComponent.class).scoreNeeded){
            player.get().get(PlayerComponent.class).score = 0;
            player.get().get(PlayerComponent.class).scoreNeeded = (int) (player.get().get(PlayerComponent.class).scoreNeeded * 1.5);
            player.get().get(DifficultyComponent.class).difficulty += 1;

            // create lootcrate for leveling up
            Entity lootcrate = new BonusFactory().makeBonus("type2");
            SoundLoader.getInstance().play(BonusLoader.Type.CRATE);
            double X = player.get().get(PositionComponent.class).xPosition;
            double Y = player.get().get(PositionComponent.class).yPosition - 250;
            lootcrate.add(new PositionComponent(X,Y));
            BonusLoader bonuss = BonusLoader.getInstance();
            lootcrate.add(new TextureComponent(bonuss.getTexture(BonusLoader.Type.CRATE)));
            lootcrate.add(new HitBoxComponent(X - bonuss.getTexture(BonusLoader.Type.CRATE).getWidth()/2, Y -bonuss.getTexture(BonusLoader.Type.CRATE).getHeight()/2, bonuss.getTexture(BonusLoader.Type.CRATE).getWidth(), bonuss.getTexture(BonusLoader.Type.CRATE).getHeight()));
            engine.add(lootcrate);
            if (isBetween(player.get().get(DifficultyComponent.class).difficulty, 1, 6)){
                player.get().get(DifficultyComponent.class).enemies += 2;
            } else if (player.get().get(DifficultyComponent.class).difficulty == 7){
                player.get().get(DifficultyComponent.class).enemies = 7;
                player.get().get(DifficultyComponent.class).enemyType = "EA";
                // give player a new weapon
                engine.add(new BonusFactory().makeBonus("type1"));
            } else if (isBetween(player.get().get(DifficultyComponent.class).difficulty, 8, 12)){
                player.get().get(DifficultyComponent.class).enemies += 1;
            } else if (player.get().get(DifficultyComponent.class).difficulty == 13){
                player.get().get(DifficultyComponent.class).enemies = 5;
                player.get().get(DifficultyComponent.class).enemyType = "Investors";
                // give player a new weapon
                engine.add(new BonusFactory().makeBonus("type1"));
            } else if (isBetween(player.get().get(DifficultyComponent.class).difficulty, 14, 20)){
                player.get().get(DifficultyComponent.class).enemies += 1;
            } else System.exit(0);
        }
        if (player.get().get(PlayerComponent.class).hp <= 0){
            player.get().get(DifficultyComponent.class).enemies = 0;
            player.get().get(PlayerComponent.class).score = 0;
            player.get().get(PlayerComponent.class).scoreNeeded = 100;
        }
    }
}