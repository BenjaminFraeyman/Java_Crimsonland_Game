/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.*;
import Factories.BulletFactory;
import Loaders.BonusLoader;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import java.awt.geom.Rectangle2D;
import java.util.Optional;

public class BonusSystem implements EntitySystem{
    @Inject
    private Engine engine;

    @Override
    public void update() {
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);
        for(Entity bonus : engine.getEntitiesWithComponents(BonusComponent.class, ConditionalComponent.class)){
            player.get().get(WeaponComponent.class).weaponType = bonus.get(BonusComponent.class).name;
            try { engine.registerSystem(WeaponSystem.class); } catch (Exception e) {  e.printStackTrace(); }
            engine.remove(bonus);
        }
        for(Entity bonus : engine.getEntitiesWithComponents(BonusComponent.class, PositionComponent.class)){
            if (player.get().get(HitBoxComponent.class).hitbox.intersects(bonus.get(HitBoxComponent.class).hitbox)){
                if (!bonus.get(BonusComponent.class).opened){
                    BonusLoader bonuss = BonusLoader.getInstance();
                    bonus.get(BonusComponent.class).opened = true;
                    if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Health")){
                        bonus.get(TextureComponent.class).image = bonuss.getTexture(BonusLoader.Type.HEALTH);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Ammo")){
                        bonus.get(TextureComponent.class).image = bonuss.getTexture(BonusLoader.Type.AMMO);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Speed")){
                        bonus.get(TextureComponent.class).image = bonuss.getTexture(BonusLoader.Type.SPEED);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Damage")){
                        bonus.get(TextureComponent.class).image = bonuss.getTexture(BonusLoader.Type.DAMAGE);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Freeze")){
                        bonus.get(TextureComponent.class).image = bonuss.getTexture(BonusLoader.Type.FREEZE);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Fire")){
                        bonus.get(TextureComponent.class).image = bonuss.getTexture(BonusLoader.Type.FIRE);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Shield")){
                        bonus.get(TextureComponent.class).image = bonuss.getTexture(BonusLoader.Type.SHIELD);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Bomb")){
                        bonus.get(TextureComponent.class).image = bonuss.getTexture(BonusLoader.Type.BOMB);
                    }
                    bonus.get(PositionComponent.class).yPosition += 250;
                    bonus.get(HitBoxComponent.class).hitbox = new Rectangle2D.Double(bonus.get(PositionComponent.class).xPosition - bonus.get(TextureComponent.class).image.getWidth()/2,
                                                                                     bonus.get(PositionComponent.class).yPosition - bonus.get(TextureComponent.class).image.getHeight()/2,
                                                                                        bonus.get(TextureComponent.class).image.getWidth(), bonus.get(TextureComponent.class).image.getHeight());
                } else {
                    if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Health")){
                        player.get().get(PlayerComponent.class).hp = player.get().get(PlayerComponent.class).maxHp;
                        System.out.println("hp");
                        engine.remove(bonus);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Ammo")){
                        player.get().get(WeaponComponent.class).ammocapacity += 5;
                        System.out.println("ammo");
                        engine.remove(bonus);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Speed")){
                        player.get().get(VelocityComponent.class).velocity += 0.5;
                        System.out.println("speed");
                        engine.remove(bonus);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Damage")){
                        player.get().get(PlayerComponent.class).damageMultiplier += 0.1;
                        System.out.println("damage");
                        engine.remove(bonus);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Freeze")){
                        try { engine.registerSystem(FrozenSystem.class); } catch (Exception e) { e.printStackTrace(); }
                        System.out.println("frozen");
                        engine.remove(bonus);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Fire")){
                        try { engine.registerSystem(FireSystem.class); } catch (Exception e) { e.printStackTrace(); }
                        System.out.println("fire");
                        engine.remove(bonus);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Shield")){
                        try { engine.registerSystem(ShieldSystem.class); } catch (Exception e) { e.printStackTrace(); }
                        System.out.println("shield");
                        engine.remove(bonus);
                    } else if (bonus.get(BonusComponent.class).name.equalsIgnoreCase("Bomb")){
                        TransformComponent trans = new TransformComponent();
                        trans.velocity = 0;
                        engine.add(new BulletFactory().makeBomb(player.get().get(PositionComponent.class), trans));
                        System.out.println("bomb");
                        engine.remove(bonus);
                    }
                    else engine.remove(bonus);
                }
            }
        }
    }
}