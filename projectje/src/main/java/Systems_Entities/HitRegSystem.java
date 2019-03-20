/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.*;
import Loaders.BonusLoader;
import Loaders.SoundLoader;
import Loaders.SpriteLoader;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Optional;

public class HitRegSystem implements EntitySystem {
    @Inject
    private Engine engine;

    @Override
    public void update() {
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);
        for (Entity enemies : engine.getEntitiesWithComponents(EnemyComponent.class)){
            for (Entity bullets : engine.getEntitiesWithComponents(BulletComponent.class)){
                if (enemies.get(HitBoxComponent.class).hitbox.intersects(bullets.get(HitBoxComponent.class).hitbox) || bullets.get(BulletComponent.class).detonate){
                    if (player.get().get(WeaponComponent.class).weapon.equals(SpriteLoader.Type.LAUNCHER) || bullets.get(BulletComponent.class).detonate){
                        // if grenade impacts enemy -> detonate
                        Entity explosion = new Entity();
                        explosion.add(new AnimatedComponent());
                        explosion.add(bullets.get(PositionComponent.class));
                        engine.add(explosion);
                        SoundLoader.getInstance().play(BonusLoader.Type.BOMB);
                        Point blast = new Point();
                        blast.x = (int)bullets.get(PositionComponent.class).xPosition;
                        blast.y = (int)bullets.get(PositionComponent.class).yPosition;
                        for (Entity enemies2 : engine.getEntitiesWithComponents(EnemyComponent.class)){
                            if (blast.distance(enemies2.get(PositionComponent.class).xPosition, enemies2.get(PositionComponent.class).yPosition) <= bullets.get(BulletComponent.class).radius){
                                enemies2.get(EnemyComponent.class).hp -= bullets.get(BulletComponent.class).damage * player.get().get(PlayerComponent.class).damageMultiplier;
                            }
                        }
                    }
                    else enemies.get(EnemyComponent.class).hp -= bullets.get(BulletComponent.class).damage * player.get().get(PlayerComponent.class).damageMultiplier;
                    engine.remove(bullets);
                }
            }
            // remove enemy if dead and reward player the xp he deserves
            if (enemies.get(EnemyComponent.class).hp <= 0){
                player.get().get(PlayerComponent.class).killCounter +=1;
                SoundLoader.getInstance().play(SpriteLoader.Type.DEATH);
                Optional<Entity> bloodSpatter = engine.getEntityWithComponents(BloodSpatterComponent.class);
                bloodSpatter.get().get(BloodSpatterComponent.class).spatterList.add(new Point2D.Double(enemies.get(PositionComponent.class).xPosition, enemies.get(PositionComponent.class).yPosition));
                player.get().get(PlayerComponent.class).score += enemies.get(EnemyComponent.class).xpReward;
                engine.remove(enemies);
                player.get().get(DifficultyComponent.class).cEnemies = player.get().get(DifficultyComponent.class).cEnemies - 1;
            }
            // deal damage when hit by enemy
            if (enemies.get(HitBoxComponent.class).hitbox.intersects(player.get().get(HitBoxComponent.class).hitbox)){
                if (!player.get().get(ShieldComponent.class).active){
                    player.get().get(PlayerComponent.class).hp -= enemies.get(EnemyComponent.class).damage;
                    if (player.get().get(PlayerComponent.class).hp <= 0){
                        player.get().get(PlayerComponent.class).hp = 0;
                        System.out.println("DEAD");
                        /*try {
                            EntitySystem test =  engine.registerSystem(DeathSystem.class);
                            test.update();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                        // if player is dead just close the program (simple way, not the best)
                        System.exit(0);
                        /*
                        Optional<Entity> bloodSpatter = engine.getEntityWithComponents(BloodSpatterComponent.class);
                        bloodSpatter.get().get(BloodSpatterComponent.class).spatterList.clear();
                        player.get().get(PlayerComponent.class).hp = 100;
                        player.get().get(DifficultyComponent.class).difficulty = 1;
                        player.get().get(PlayerComponent.class).score = 0;
                        player.get().get(PlayerComponent.class).scoreNeeded = 100;
                        */
                    }
                }
                engine.remove(enemies);
                player.get().get(DifficultyComponent.class).cEnemies = player.get().get(DifficultyComponent.class).cEnemies - 1;
            }
        }
    }
}