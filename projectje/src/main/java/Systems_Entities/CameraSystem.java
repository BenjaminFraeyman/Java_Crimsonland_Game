/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.*;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import org.ibcn.gso.project.config.GraphicsConfig;
import java.util.Optional;

public class CameraSystem implements EntitySystem {
    @Inject
    private Engine engine;

    @Inject
    private GraphicsConfig graphicsConfig;

    @Override
    public void update() {
        Optional<Entity> world = engine.getEntityWithComponents(WorldComponent.class);
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);
        Optional<Entity> camera = engine.getEntityWithComponents(CameraComponent.class);

        double maxTranslateX =  world.get().get(WorldComponent.class).image.getWidth() - graphicsConfig.getScreenWidth();
        double minTranslateX =  0;
        double translatedX = (player.get().get(PositionComponent.class).xPosition - graphicsConfig.getScreenWidth()/2);

        double maxTranslateY =  world.get().get(WorldComponent.class).image.getHeight() - graphicsConfig.getScreenHeight();
        double minTranslateY =  0;
        double translatedY = (player.get().get(PositionComponent.class).yPosition - graphicsConfig.getScreenHeight()/2);

        if ( translatedX > maxTranslateX){
            camera.get().get(CameraComponent.class).translatedX = maxTranslateX;
        } else if(translatedX < minTranslateX){
            camera.get().get(CameraComponent.class).translatedX = minTranslateX;
        } else {
            camera.get().get(CameraComponent.class).translatedX = translatedX;
        }

        if (translatedY > maxTranslateY){
            camera.get().get(CameraComponent.class).translatedY = maxTranslateY;
        } else if(translatedY < minTranslateY){
            camera.get().get(CameraComponent.class).translatedY = minTranslateY;
        } else {
            camera.get().get(CameraComponent.class).translatedY = translatedY;
        }
    }
}