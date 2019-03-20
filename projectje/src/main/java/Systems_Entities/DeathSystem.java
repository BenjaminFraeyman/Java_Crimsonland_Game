/* Author: Benjamin Fraeyman */
package Systems_Entities;
import Components.*;
import Loaders.SpriteLoader;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
public class DeathSystem implements EntitySystem {
    @Inject
    private Engine engine;
    private  final SpriteLoader spro = SpriteLoader.getInstance();
    @Override
    public void update() {
        for(Entity player : engine.getEntitiesWithComponents(PlayerComponent.class)){
            engine.remove(player);
        }
        Entity player2 = new Entity();
        player2.add(new PlayerComponent());
        player2.add(new PositionComponent(500,500));
        player2.add(new WeaponComponent());
        player2.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.PLAYER)));
        player2.add(new DifficultyComponent());
        player2.add(new HitBoxComponent(500, 500, player2.get(TextureComponent.class).image.getWidth(), player2.get(TextureComponent.class).image.getHeight()));
        player2.add(new VelocityComponent());
        player2.add(new ShieldComponent());
        engine.add(player2);
        for(Entity enemy : engine.getEntitiesWithComponents(EnemyComponent.class)){
            engine.remove(enemy);
        }
        for(Entity bonus : engine.getEntitiesWithComponents(BonusComponent.class)){
            engine.remove(bonus);
        }
        engine.unregisterSystem(this);
    }
}