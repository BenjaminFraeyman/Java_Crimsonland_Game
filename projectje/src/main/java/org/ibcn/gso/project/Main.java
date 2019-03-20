package org.ibcn.gso.project;

import javafx.application.Application;
import javafx.stage.Stage;
import org.ibcn.gso.esf.EngineFactory;
import org.ibcn.gso.project.config.ConfigReader;
import java.util.function.Consumer;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Consumer<Double> fpsListener = fps -> primaryStage.setTitle("Moon Battles BattleBack 2 – Enhanced Lootbox Edition - " + fps + " FPS");

            // Create game
            Game game = new Game(EngineFactory.create(), ConfigReader.load("config.yml"), fpsListener);
                     
            // Setup stage
            primaryStage.setScene(game.getScene());
            primaryStage.show();

            // Start game
            game.start();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
