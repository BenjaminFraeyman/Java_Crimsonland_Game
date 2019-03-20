/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.*;
import Factories.BulletFactory;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import Loaders.SpriteLoader;
import org.ibcn.gso.project.config.ControlsConfig;
import java.util.Optional;

public class InputSystem implements EntitySystem {
    @Inject
    private Engine engine;

    @Inject
    private ControlsConfig controlsConfig;

    private  final SpriteLoader spro = SpriteLoader.getInstance();
    private boolean previous = false;
    private int counter = 0;

    @Override
    public void update() {
        int xCombined = 0;
        int yCombined = 0;
        boolean moving = false;
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);
        Optional<Entity> input = engine.getEntityWithComponents(KeyPressedComponent.class);
        for (String key : input.get().get(KeyPressedComponent.class).keyCodeList){
            if (key.equalsIgnoreCase(controlsConfig.getMoveUp())){
                yCombined = yCombined - 1;
                moving = true;
            }
            else if (key.equalsIgnoreCase(controlsConfig.getMoveDown())){
                yCombined = yCombined + 1;
                moving = true;
            }
            else if (key.equalsIgnoreCase(controlsConfig.getMoveLeft())){
                xCombined = xCombined - 1;
                moving = true;
            }
            else if (key.equalsIgnoreCase(controlsConfig.getMoveRight())){
                xCombined = xCombined + 1;
                moving = true;
            }
            else if (key.equalsIgnoreCase(controlsConfig.getReload())){
                player.get().get(WeaponComponent.class).reloading = true;
            }
            else if (key.equalsIgnoreCase(controlsConfig.getMaxHP()) & controlsConfig.chooser){
                player.get().get(PlayerComponent.class).hp = player.get().get(PlayerComponent.class).maxHp;
            }
            else if (key.equalsIgnoreCase(controlsConfig.getLauncher()) & controlsConfig.chooser){
                player.get().get(WeaponComponent.class).weaponType = "launcher";
                try { engine.registerSystem(WeaponSystem.class); } catch (Exception e) {  e.printStackTrace(); }
            }
            else if (key.equalsIgnoreCase(controlsConfig.getSHOTGUN()) & controlsConfig.chooser){
                player.get().get(WeaponComponent.class).weaponType = "shotgun";
                try { engine.registerSystem(WeaponSystem.class); } catch (Exception e) {  e.printStackTrace(); }
            }
            else if (key.equalsIgnoreCase(controlsConfig.getIncrDamage()) & controlsConfig.chooser){
                player.get().get(PlayerComponent.class).damageMultiplier += 1;
            }
            TransformComponent transformComponent = new TransformComponent();
            transformComponent.xCombined = xCombined;
            transformComponent.yCombined = yCombined;
            player.get().add(transformComponent);
        }
        if (!moving){
            player.get().get(TextureComponent.class).image = spro.getTexture(SpriteLoader.Type.PLAYER);
        }
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.xCombined = xCombined;
        transformComponent.yCombined = yCombined;
        if (input.get().get(KeyPressedComponent.class).mousePressed & (player.get().get(WeaponComponent.class).ammo > 0)){
            counter++;
            if (counter == 75){
                for (int i = 0; i < player.get().get(WeaponComponent.class).pellets; i++) {
                    engine.add(new BulletFactory().makeBullet(player.get().get(WeaponComponent.class).weaponType, transformComponent));
                }
                player.get().get(WeaponComponent.class).ammo -= 1;
                counter = 0;
            }
            if(!previous){
                for (int i = 0; i < player.get().get(WeaponComponent.class).pellets; i++) {
                    engine.add(new BulletFactory().makeBullet(player.get().get(WeaponComponent.class).weaponType, transformComponent));
                }
                player.get().get(WeaponComponent.class).ammo -= 1;
            }

        }
        previous = input.get().get(KeyPressedComponent.class).mousePressed;

        for(Entity crosshair : engine.getEntitiesWithComponents(CrosshairComponent.class)){
            crosshair.get(PositionComponent.class).xPosition = input.get().get(KeyPressedComponent.class).mouseXPosition;
            crosshair.get(PositionComponent.class).yPosition = input.get().get(KeyPressedComponent.class).mouseYPosition;
        }
    }
}


// geprobeerd om game te resetten na dood
/*
package Systems_Entities;
import Components.*;
import Factories.BulletFactory;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import Loaders.SpriteLoader;
import org.ibcn.gso.project.config.ControlsConfig;
import java.util.Optional;

public class InputSystem implements EntitySystem {
    @Inject
    private Engine engine;
    @Inject
    private ControlsConfig controlsConfig;
    private  final SpriteLoader spro = SpriteLoader.getInstance();
    private boolean previous = false;
    private int counter = 0;
    @Override
    public void update() {
        int xCombined = 0;
        int yCombined = 0;
        boolean moving = false;
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);
        Optional<Entity> input = engine.getEntityWithComponents(KeyPressedComponent.class);
        if (player.get().get(PlayerComponent.class).started){
            for (String key : input.get().get(KeyPressedComponent.class).keyCodeList){
                if (key.equalsIgnoreCase(controlsConfig.getMoveUp())){
                    yCombined = yCombined - 1;
                    moving = true;
                }
                else if (key.equalsIgnoreCase(controlsConfig.getMoveDown())){
                    yCombined = yCombined + 1;
                    moving = true;
                }
                else if (key.equalsIgnoreCase(controlsConfig.getMoveLeft())){
                    xCombined = xCombined - 1;
                    moving = true;
                }
                else if (key.equalsIgnoreCase(controlsConfig.getMoveRight())){
                    xCombined = xCombined + 1;
                    moving = true;
                }
                else if (key.equalsIgnoreCase(controlsConfig.getReload())){
                    player.get().get(WeaponComponent.class).reloading = true;
                }
                else if (key.equalsIgnoreCase(controlsConfig.getMaxHP()) & controlsConfig.chooser){
                    player.get().get(PlayerComponent.class).hp = player.get().get(PlayerComponent.class).maxHp;
                }
                else if (key.equalsIgnoreCase(controlsConfig.getLauncher()) & controlsConfig.chooser){
                    player.get().get(WeaponComponent.class).weaponType = "launcher";
                    try { engine.registerSystem(WeaponSystem.class); } catch (Exception e) {  e.printStackTrace(); }
                }
                else if (key.equalsIgnoreCase(controlsConfig.getSHOTGUN()) & controlsConfig.chooser){
                    player.get().get(WeaponComponent.class).weaponType = "shotgun";
                    try { engine.registerSystem(WeaponSystem.class); } catch (Exception e) {  e.printStackTrace(); }
                }
                else if (key.equalsIgnoreCase(controlsConfig.getIncrDamage()) & controlsConfig.chooser){
                    player.get().get(PlayerComponent.class).damageMultiplier += 1;
                }
                TransformComponent transformComponent = new TransformComponent();
                transformComponent.xCombined = xCombined;
                transformComponent.yCombined = yCombined;
                player.get().add(transformComponent);
            }
            if (!moving){
                player.get().get(TextureComponent.class).image = spro.getTexture(SpriteLoader.Type.PLAYER);
            }
            TransformComponent transformComponent = new TransformComponent();
            transformComponent.xCombined = xCombined;
            transformComponent.yCombined = yCombined;
            if (input.get().get(KeyPressedComponent.class).mousePressed & (player.get().get(WeaponComponent.class).ammo > 0)){
                counter++;
                if (counter == 75){
                    for (int i = 0; i < player.get().get(WeaponComponent.class).pellets; i++) {
                        engine.add(new BulletFactory().makeBullet(player.get().get(WeaponComponent.class).weaponType, transformComponent));
                    }
                    player.get().get(WeaponComponent.class).ammo -= 1;
                    counter = 0;
                }
                if(!previous){
                    for (int i = 0; i < player.get().get(WeaponComponent.class).pellets; i++) {
                        engine.add(new BulletFactory().makeBullet(player.get().get(WeaponComponent.class).weaponType, transformComponent));
                    }
                    player.get().get(WeaponComponent.class).ammo -= 1;
                }

            }
            previous = input.get().get(KeyPressedComponent.class).mousePressed;
        } else {
            try {
                engine.unregisterSystem(engine.getSystem(WeaponSystem.class).get());
                engine.unregisterSystem(engine.getSystem(ReloadSystem.class).get());
                engine.unregisterSystem(engine.getSystem(BulletSystem.class).get());
                engine.unregisterSystem(engine.getSystem(HitRegSystem.class).get());
                engine.unregisterSystem(engine.getSystem(SpawnerSystem.class).get());
                engine.unregisterSystem(engine.getSystem(XPSystem.class).get());
                engine.unregisterSystem(engine.getSystem(BonusSystem.class).get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (String key : input.get().get(KeyPressedComponent.class).keyCodeList){
            if (key.equalsIgnoreCase(controlsConfig.getRestart())){
                player.get().get(PlayerComponent.class).started = true;
                try {
                    engine.registerSystem(WeaponSystem.class);
                    engine.registerSystem(ReloadSystem.class);
                    engine.registerSystem(BulletSystem.class);
                    engine.registerSystem(HitRegSystem.class);
                    engine.registerSystem(SpawnerSystem.class);
                    engine.registerSystem(XPSystem.class);
                    engine.registerSystem(BonusSystem.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for(Entity crosshair : engine.getEntitiesWithComponents(CrosshairComponent.class)){
            crosshair.get(PositionComponent.class).xPosition = input.get().get(KeyPressedComponent.class).mouseXPosition;
            crosshair.get(PositionComponent.class).yPosition = input.get().get(KeyPressedComponent.class).mouseYPosition;
        }
    }
}*/