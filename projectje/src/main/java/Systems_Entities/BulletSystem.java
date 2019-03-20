/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.*;
import Loaders.SpriteLoader;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class BulletSystem implements EntitySystem {
    @Inject
    private Engine engine;

    @Override
    public void update() {
        Optional<Entity> crosshair = engine.getEntityWithComponents(CrosshairComponent.class);
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);

        // mouse positions relative to player
        Optional<Entity> camera = engine.getEntityWithComponents(CameraComponent.class);
        double mouseXPosition = crosshair.get().get(PositionComponent.class).xPosition + camera.get().get(CameraComponent.class).translatedX;
        double mouseYPosition = crosshair.get().get(PositionComponent.class).yPosition + camera.get().get(CameraComponent.class).translatedY;

        // generate a bullet if it contains a transform
        for(Entity bullet :engine.getEntitiesWithComponents(TransformComponent.class, BulletComponent.class)){
            bullet.get(PositionComponent.class).yPosition = player.get().get(PositionComponent.class).yPosition;
            bullet.get(PositionComponent.class).xPosition = player.get().get(PositionComponent.class).xPosition;
            bullet.get(PositionComponent.class).rPosition = Math.atan2(player.get().get(PositionComponent.class).yPosition - mouseYPosition, player.get().get(PositionComponent.class).xPosition - mouseXPosition);
            double randomNum2 = Math.toRadians(ThreadLocalRandom.current().nextInt(-player.get().get(WeaponComponent.class).spread, 2*player.get().get(WeaponComponent.class).spread));
            bullet.get(PositionComponent.class).rPosition += randomNum2;
            bullet.remove(TransformComponent.class);
            bullet.add(new VelocityComponent());
            bullet.add(new HitBoxComponent(bullet.get(PositionComponent.class).xPosition, bullet.get(PositionComponent.class).yPosition, bullet.get(TextureComponent.class).image.getWidth(), bullet.get(TextureComponent.class).image.getHeight()));
            SoundComponent test = new SoundComponent();
            test.sound = player.get().get(WeaponComponent.class).weapon;
            bullet.add(test);
        }

        // move bullet if it contains a velocity
        for(Entity bullet :engine.getEntitiesWithComponents(VelocityComponent.class, BulletComponent.class)){
            // remove bullet when it exceeds its range
            bullet.get(BulletComponent.class).range -= bullet.get(VelocityComponent.class).velocity;
            if (bullet.get(BulletComponent.class).range <= 0){
                if (player.get().get(WeaponComponent.class).weapon.equals(SpriteLoader.Type.LAUNCHER) || bullet.get(BulletComponent.class).detonate){
                    bullet.get(VelocityComponent.class).velocity = 0;
                    bullet.get(BulletComponent.class).detonate = true;
                }
                else engine.remove(bullet);
            }
            // update bulletposition
            bullet.get(PositionComponent.class).yPosition -= bullet.get(VelocityComponent.class).velocity * Math.sin(bullet.get(PositionComponent.class).rPosition);
            bullet.get(PositionComponent.class).xPosition -= bullet.get(VelocityComponent.class).velocity * Math.cos(bullet.get(PositionComponent.class).rPosition);
            bullet.get(HitBoxComponent.class).hitbox.setRect(bullet.get(PositionComponent.class).xPosition, bullet.get(PositionComponent.class).yPosition, bullet.get(TextureComponent.class).image.getWidth(), bullet.get(TextureComponent.class).image.getHeight());
        }
    }
}