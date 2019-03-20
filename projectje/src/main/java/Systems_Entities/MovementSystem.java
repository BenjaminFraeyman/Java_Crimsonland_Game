/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.*;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import Loaders.SpriteLoader;
import org.ibcn.gso.project.config.GraphicsConfig;
import java.util.Optional;

public class MovementSystem implements EntitySystem {
    @Inject
    private Engine engine;

    @Inject
    private GraphicsConfig graphicsConfig;

    private  final SpriteLoader spro = SpriteLoader.getInstance();

    @Override
    public void update() {
        Optional<Entity> world = engine.getEntityWithComponents(WorldComponent.class);
        Optional<Entity> crosshair = engine.getEntityWithComponents(CrosshairComponent.class);
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);

        // MOUSE MOVEMENT -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*
        // mouse positions relative to player
        Optional<Entity> camera = engine.getEntityWithComponents(CameraComponent.class);
        double mouseXPosition = crosshair.get().get(PositionComponent.class).xPosition + camera.get().get(CameraComponent.class).translatedX;
        double mouseYPosition = crosshair.get().get(PositionComponent.class).yPosition + camera.get().get(CameraComponent.class).translatedY;
        // mouse hitbox
        double width = crosshair.get().get(HitBoxComponent.class).hitbox.getWidth();
        double height = crosshair.get().get(HitBoxComponent.class).hitbox.getHeight();
        crosshair.get().get(HitBoxComponent.class).hitbox.setRect(crosshair.get().get(PositionComponent.class).xPosition, crosshair.get().get(PositionComponent.class).yPosition, width, height);
        // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*

        // PLAYER MOVEMENT ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*
        double angle = Math.atan2(player.get().get(PositionComponent.class).yPosition - mouseYPosition, player.get().get(PositionComponent.class).xPosition - mouseXPosition) * 180 / Math.PI ;
        player.get().get(PositionComponent.class).rPosition = angle;
        for(Entity entity :engine.getEntitiesWithComponents(TransformComponent.class, PlayerComponent.class)){
            double direction = Math.atan2(entity.get(TransformComponent.class).yCombined, entity.get(TransformComponent.class).xCombined);
            // if either vectors is 0 -> player is moving
            if (!(entity.get(TransformComponent.class).yCombined == 0 & entity.get(TransformComponent.class).xCombined == 0)){
                // set player to animated texture
                entity.get(TextureComponent.class).image = spro.getTexture(SpriteLoader.Type.MOVINGPLAYER);
                double y = entity.get(PositionComponent.class).yPosition + entity.get(VelocityComponent.class).velocity * Math.sin(direction);
                if (y < world.get().get(WorldComponent.class).image.getHeight() & y > 0){
                    entity.get(PositionComponent.class).yPosition += entity.get(VelocityComponent.class).velocity * Math.sin(direction);
                }
                double x = entity.get(PositionComponent.class).xPosition + entity.get(VelocityComponent.class).velocity * Math.cos(direction);
                if (x < world.get().get(WorldComponent.class).image.getWidth() & x > 0){
                    entity.get(PositionComponent.class).xPosition += entity.get(VelocityComponent.class).velocity * Math.cos(direction);
                }
                width = entity.get(HitBoxComponent.class).hitbox.getWidth();
                height = entity.get(HitBoxComponent.class).hitbox.getHeight();
                entity.get(HitBoxComponent.class).hitbox.setRect(entity.get(PositionComponent.class).xPosition - width/2, entity.get(PositionComponent.class).yPosition - height/2, width, height);
            }
            entity.remove(TransformComponent.class);
        }
        // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*

        // ENEMY MOVEMENT -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*
        for(Entity enemy :engine.getEntitiesWithComponents(EnemyComponent.class)){
            enemy.get(TextureComponent.class).image = spro.getTexture(enemy.get(EnemyComponent.class).type);
            double direction = Math.atan2(player.get().get(PositionComponent.class).yPosition - enemy.get(PositionComponent.class).yPosition, player.get().get(PositionComponent.class).xPosition - enemy.get(PositionComponent.class).xPosition);
            enemy.get(PositionComponent.class).yPosition += enemy.get(EnemyComponent.class).speed * Math.sin(direction);
            enemy.get(PositionComponent.class).xPosition += enemy.get(EnemyComponent.class).speed * Math.cos(direction);
            enemy.get(PositionComponent.class).rPosition = Math.toDegrees(direction);
            width = enemy.get(HitBoxComponent.class).hitbox.getWidth();
            height = enemy.get(HitBoxComponent.class).hitbox.getHeight();
            enemy.get(HitBoxComponent.class).hitbox.setRect(enemy.get(PositionComponent.class).xPosition - width/2, enemy.get(PositionComponent.class).yPosition - height/2, width, height);
        }
        // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*
    }
}