/* Author: Benjamin Fraeyman */
package Systems_Entities;

import Components.*;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.esf.EntitySystem;
import org.ibcn.gso.esf.ioc.Inject;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import Loaders.SpriteLoader;
import org.ibcn.gso.project.config.ControlsConfig;
import org.ibcn.gso.project.config.GraphicsConfig;
import javafx.scene.text.Font;
import java.awt.geom.Point2D;
import java.util.Optional;

public class RenderSystem implements EntitySystem{
    @Inject
    private Engine engine;

    private GraphicsContext gc;

    @Inject
    private GraphicsConfig graphicsConfig;

    @Inject
    private ControlsConfig controlsConfig;

    private  final SpriteLoader spro = SpriteLoader.getInstance();

    @Override
    public void update() {
        Optional<Entity> world = engine.getEntityWithComponents(WorldComponent.class);
        Optional<Entity> p = engine.getEntityWithComponents(PlayerComponent.class);
        gc = world.get().get(WorldComponent.class).canvas.getGraphicsContext2D();
        Optional<Entity> camera = engine.getEntityWithComponents(CameraComponent.class);

        // translate the canvas
        gc.translate(-camera.get().get(CameraComponent.class).translatedX, -camera.get().get(CameraComponent.class).translatedY);

        // draw the background
        gc.drawImage(world.get().get(WorldComponent.class).image, world.get().get(PositionComponent.class).xPosition, world.get().get(PositionComponent.class).yPosition);

        // draw the bloodspatters on the background
        for(Entity bloodSpatters :engine.getEntitiesWithComponents(BloodSpatterComponent.class)){
            for (Point2D location : bloodSpatters.get(BloodSpatterComponent.class).spatterList) {
                gc.drawImage(bloodSpatters.get(TextureComponent.class).image, location.getX(), location.getY());
            }
        }

        // draw the player
        for(Entity player :engine.getEntitiesWithComponents(PlayerComponent.class)){
            ImageView iv = new ImageView(player.get(TextureComponent.class).image);
            iv.setRotate(player.get(PositionComponent.class).rPosition - 90);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            Image rotatedImage = iv.snapshot(params, null);
            if (player.get(PlayerComponent.class).hp <= 0){ // implementatie niet werkend gekregen
                String deathnote = "<- hover here to restart";
                gc.setFont(Font.font(30));
                gc.fillText(deathnote, player.get(PositionComponent.class).xPosition, player.get(PositionComponent.class).yPosition);
            } else {
                gc.drawImage(rotatedImage , player.get(PositionComponent.class).xPosition - rotatedImage.getWidth()/2, player.get(PositionComponent.class).yPosition - rotatedImage.getHeight()/2);
                if (player.get(ShieldComponent.class).active){
                    gc.drawImage(spro.getTexture(SpriteLoader.Type.ICE) , player.get(PositionComponent.class).xPosition - spro.getTexture(SpriteLoader.Type.ICE).getWidth()/2, player.get(PositionComponent.class).yPosition - spro.getTexture(SpriteLoader.Type.ICE).getHeight()/2);
                }
            }
        }

        // draw the bullets
        for(Entity bullets :engine.getEntitiesWithComponents(BulletComponent.class)){
            ImageView iv = new ImageView(bullets.get(TextureComponent.class).image);
            iv.setRotate(Math.toDegrees(bullets.get(PositionComponent.class).rPosition) - 90);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            Image rotatedImage = iv.snapshot(params, null);
            gc.drawImage(rotatedImage , bullets.get(PositionComponent.class).xPosition - rotatedImage.getWidth()/2, bullets.get(PositionComponent.class).yPosition - rotatedImage.getHeight()/2);
        }
        for(Entity explosions :engine.getEntitiesWithComponents(PositionComponent.class, AnimatedComponent.class)){
            try {
                Image test = spro.getTexture(explosions.get(AnimatedComponent.class).weapon, explosions.get(AnimatedComponent.class).counter);
                gc.drawImage(test, explosions.get(PositionComponent.class).xPosition - test.getWidth()/2, explosions.get(PositionComponent.class).yPosition - test.getHeight()/2 );
                explosions.get(AnimatedComponent.class).counter += 1;
            } catch (Exception e) {
                engine.remove(explosions);
            }
        }

        // draw the enemies
        if (p.get().get(PlayerComponent.class).hp > 0){
            for(Entity enemy :engine.getEntitiesWithComponents(EnemyComponent.class)){
                ImageView iv = new ImageView(enemy.get(TextureComponent.class).image);
                iv.setRotate(enemy.get(PositionComponent.class).rPosition + 90);
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                Image rotatedImage = iv.snapshot(params, null);
                gc.drawImage(rotatedImage , enemy.get(PositionComponent.class).xPosition - rotatedImage.getWidth()/2, enemy.get(PositionComponent.class).yPosition - rotatedImage.getHeight()/2);
            }

            // draw the lootcrate
            for(Entity crate :engine.getEntitiesWithComponents(BonusComponent.class, TextureComponent.class)){
                gc.drawImage(crate.get(TextureComponent.class).image , crate.get(PositionComponent.class).xPosition - crate.get(TextureComponent.class).image.getWidth()/2, crate.get(PositionComponent.class).yPosition - crate.get(TextureComponent.class).image.getHeight()/2);
            }
        }

        // translate back
        gc.translate(camera.get().get(CameraComponent.class).translatedX, camera.get().get(CameraComponent.class).translatedY);

        // draw the crosshair
        for(Entity crosshair :engine.getEntitiesWithComponents(CrosshairComponent.class)){
            gc.drawImage(crosshair.get(TextureComponent.class).image ,
                    crosshair.get(PositionComponent.class).xPosition - crosshair.get(TextureComponent.class).image.getWidth()/2,
                    crosshair.get(PositionComponent.class).yPosition - crosshair.get(TextureComponent.class).image.getHeight()/2);
        }

        double tx;
        double tx2;
        Optional<Entity> player = engine.getEntityWithComponents(PlayerComponent.class);
        // draw healthbar + hp
        gc.setFill(new Color(1, 0.1412, 0.3412, 1));
        tx = player.get().get(PlayerComponent.class).maxHp / player.get().get(PlayerComponent.class).maxHp * 100;
        tx *= 5;
        gc.fillRect(graphicsConfig.getScreenWidth()/2 - tx/2, graphicsConfig.getScreenHeight()/200, tx, 20);
        gc.setFill(new Color(0.1686, 1, 0.0667, 1));
        tx2 = (double)player.get().get(PlayerComponent.class).hp / player.get().get(PlayerComponent.class).maxHp * 100;
        tx2 *= 5;
        gc.fillRect(graphicsConfig.getScreenWidth()/2 - tx/2, graphicsConfig.getScreenHeight()/200, tx2, 20);
        String hp = player.get().get(PlayerComponent.class).hp + "/" + player.get().get(PlayerComponent.class).maxHp;
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(22));
        gc.fillText(hp, graphicsConfig.getScreenWidth()/2 - 15, 23);

        // draw xpbar + xp
        gc.setFill(new Color(0.1529, 0, 1, 1));
        tx = player.get().get(PlayerComponent.class).scoreNeeded / player.get().get(PlayerComponent.class).scoreNeeded * 100;
        tx *= 5;
        gc.fillRect(graphicsConfig.getScreenWidth()/2 - tx/2, graphicsConfig.getScreenHeight() - 20, tx, 20);
        gc.setFill(new Color(0, 0.9373, 1, 1));
        tx2 = (double)player.get().get(PlayerComponent.class).score / player.get().get(PlayerComponent.class).scoreNeeded * 100;
        tx2 *= 5;
        gc.fillRect(graphicsConfig.getScreenWidth()/2 - tx/2, graphicsConfig.getScreenHeight() - 20, tx2, 20);
        String xp = player.get().get(PlayerComponent.class).score + "/" + player.get().get(PlayerComponent.class).scoreNeeded;
        gc.setFill(Color.BLACK);
        gc.fillText(xp, graphicsConfig.getScreenWidth()/2 - 15, graphicsConfig.getScreenHeight() - graphicsConfig.getScreenHeight()/200);

        // draw currentweapon + ammo
        gc.drawImage(SpriteLoader.getInstance().getTexture(player.get().get(WeaponComponent.class).weapon) ,
                graphicsConfig.getScreenWidth() - SpriteLoader.getInstance().getTexture(player.get().get(WeaponComponent.class).weapon).getWidth(),
                graphicsConfig.getScreenHeight() - SpriteLoader.getInstance().getTexture(player.get().get(WeaponComponent.class).weapon).getHeight());
        String bullets = player.get().get(WeaponComponent.class).ammo + "/" + player.get().get(WeaponComponent.class).ammocapacity;
        gc.setFont(Font.font(24));
        gc.fillText(bullets, graphicsConfig.getScreenWidth() - 75, graphicsConfig.getScreenHeight() - 25);

        String damage = "DamageMultiplier= " + Double.toString(player.get().get(PlayerComponent.class).damageMultiplier);
        gc.setFont(Font.font(24));
        gc.fillText(damage, 75, graphicsConfig.getScreenHeight() - 25);

        String kills = "Kills= " + Integer.toString(player.get().get(PlayerComponent.class).killCounter);
        gc.setFont(Font.font(24));
        gc.fillText(kills, 75, graphicsConfig.getScreenHeight() - 50);

        String speed = "Speed= " + Double.toString(player.get().get(VelocityComponent.class).velocity);
        gc.setFont(Font.font(24));
        gc.fillText(speed, 75, graphicsConfig.getScreenHeight() - 75);

        // OM DE VERBETERING MAKKELIJKER TE MAKEN
        if (controlsConfig.chooser){
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font(15));
            gc.fillText("Press H for max health", 50, 50);
            gc.fillText("Press L for Launcher", 50, 75);
            gc.fillText("Press K for shotgun", 50, 100);
            gc.fillText("Press J to increase damage", 50, 125);
        }
    }
}