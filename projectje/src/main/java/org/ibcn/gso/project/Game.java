/* Author: Benjamin Fraeyman */
package org.ibcn.gso.project;

import Components.*;
import Loaders.BonusLoader;
import Loaders.SoundLoader;
import Loaders.SpriteLoader;
import Systems_Entities.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import org.ibcn.gso.esf.Engine;
import org.ibcn.gso.esf.Entity;
import org.ibcn.gso.project.config.ControlsConfig;
import org.ibcn.gso.project.config.GameConfig;
import org.ibcn.gso.project.config.GraphicsConfig;
import java.io.FileNotFoundException;
import java.util.function.Consumer;

public class Game extends AnimationTimer {
    //Specifies how many milliseconds to wait before updating the FPS counter (must be greater than 1000)
    private static final int FPS_UPDATE_INTERVAL = 1000;
    private Scene scene;
    private Canvas canvas;
    private final Engine engine;
    private final Consumer<Double> fpsListener;
    private final GameConfig config;

    private  final SpriteLoader spro = SpriteLoader.getInstance();
    private  final SoundLoader sounds = SoundLoader.getInstance();
    private final BonusLoader bonus = BonusLoader.getInstance();

    public Game(Engine engine, GameConfig config, Consumer<Double> fpsListener) throws Exception {
        this.engine = engine;
        this.config = config;
        this.fpsListener = fpsListener;
        /**
         * Add the config objects to the shared context in Engine.
         * This enables you to use the Inject annotation in your systems to easily get access to these config objects.
         */
        engine.registerContext(GraphicsConfig.class, config.getGraphics());
        engine.registerContext(ControlsConfig.class, config.getControls());
        preload();
        initUI();
        initBackground();
        initPlayer();
        initSystems();
        initCamera();
    }

    public void preload() throws FileNotFoundException {
        //Prefetch resources that would take too long to load in-game.
        spro.loadImages();
        bonus.loadImages();
        sounds.loadSounds();
    }

    public void initBackground() {
        //Initialize the world entity.
        Entity world = new Entity();
        world.add(new WorldComponent(canvas, spro.getTexture(SpriteLoader.Type.WORLD)));
        world.add(new PositionComponent(0,0));
        engine.add(world);
    }

    public void initPlayer() {
        //Initialize the player entity.
        Entity player = new Entity();
        player.add(new PlayerComponent());
        player.add(new PositionComponent(config.getGraphics().getScreenWidth()/2,config.getGraphics().getScreenHeight()/2));
        player.add(new WeaponComponent());
        player.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.PLAYER)));
        player.add(new DifficultyComponent());
        player.add(new HitBoxComponent(config.getGraphics().getScreenWidth()/2, config.getGraphics().getScreenHeight()/2, player.get(TextureComponent.class).image.getWidth(), player.get(TextureComponent.class).image.getHeight()));
        player.add(new VelocityComponent());
        player.add(new ShieldComponent());
        engine.add(player);
    }

    public void initUI() {
        Entity input = new Entity();
        input.add(new KeyPressedComponent());
        // Set up graphics container
        canvas = new Canvas(config.getGraphics().getScreenWidth(), config.getGraphics().getScreenHeight());
        scene = new Scene(new Group(canvas));
        //Hides the OS mouse cursor.
        scene.setCursor(Cursor.NONE);
        //Set up input handling:
        EventHandler<? super MouseEvent> mouseHandler = e -> {
            //Mouse movement handler code
            input.get(KeyPressedComponent.class).mouseXPosition = e.getX();
            input.get(KeyPressedComponent.class).mouseYPosition = e.getY();
        };
        scene.setOnMouseMoved(mouseHandler);
        scene.setOnMouseDragged(mouseHandler);
        scene.setOnMousePressed(e -> {
            //Mouse pressed handler code
            input.get(KeyPressedComponent.class).mousePressed = true;
        });
        scene.setOnMouseReleased(e -> {
            //Mouse released handler code
            input.get(KeyPressedComponent.class).mousePressed = false;
        });
        scene.setOnKeyPressed(e -> {
            //Handle inputChar pressed
            if (!input.get(KeyPressedComponent.class).keyCodeList.contains(e.getCode().toString())){
                input.get(KeyPressedComponent.class).keyCodeList.add(e.getCode().toString());
        }});
        scene.setOnKeyReleased(e -> {
            //Handle inputChar released
            input.get(KeyPressedComponent.class).keyCodeList.remove(e.getCode().toString());
        });
        engine.add(input);
        initUIElements();
    }

    public void initUIElements() {
        //Initialize HUD: health & xp bar, weapon status, etc...
        //Initialize the crosshair entity.
        Entity crosshair = new Entity();
        crosshair.add(new CrosshairComponent());
        crosshair.add(new PositionComponent(0,0));
        crosshair.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.CROSSHAIR)));
        crosshair.add(new HitBoxComponent(0, 0, crosshair.get(TextureComponent.class).image.getWidth(), crosshair.get(TextureComponent.class).image.getHeight()));
        engine.add(crosshair);
        //Initialize the bloodspatter entity.
        Entity bloodSpatter = new Entity();
        bloodSpatter.add(new BloodSpatterComponent());
        bloodSpatter.add(new TextureComponent(spro.getTexture(SpriteLoader.Type.BLOOD)));
        engine.add(bloodSpatter);
    }

    public void initCamera() {
        //Set up the camera that follows the player.
        Entity camera = new Entity();
        camera.add(new CameraComponent());
        engine.add(camera);
    }

    public void initSystems() throws Exception {
        //Register the game systems with the Engine.
        engine.registerSystem(InputSystem.class);
        engine.registerSystem(MovementSystem.class);
        engine.registerSystem(CameraSystem.class);

        engine.registerSystem(WeaponSystem.class);
        engine.registerSystem(ReloadSystem.class);
        engine.registerSystem(BulletSystem.class);
        engine.registerSystem(HitRegSystem.class);
        engine.registerSystem(SpawnerSystem.class);

        engine.registerSystem(XPSystem.class);
        engine.registerSystem(BonusSystem.class);

        engine.registerSystem(SoundSystem.class);
        engine.registerSystem(RenderSystem.class);
    }

    public Scene getScene() {
        return scene;
    }
    //FPS counter variables
    private int counter;
    private long start = System.currentTimeMillis();
    /**
     * This method is executed ~60 times per second and as such implements the
     * game loop by calling update on the engine implementation.
     *
     * @param currentNanoTime The timestamp of the current frame given in
     *                        nanoseconds, can be ignored for this project.
     */
    @Override
    public void handle(long currentNanoTime) {
        long temp = System.currentTimeMillis();
        counter++;
        engine.update();
        if ((System.currentTimeMillis() - start) > FPS_UPDATE_INTERVAL) {
            fpsListener.accept(counter / (FPS_UPDATE_INTERVAL / 1000.0));
            counter = 0;
            start = System.currentTimeMillis();
        }
        System.out.println("Tijd om gameloop te doorlopen in milliseconden: " + (System.currentTimeMillis() - temp));
    }
}