package org.ibcn.gso.project.config;

import java.io.*;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.slf4j.LoggerFactory;

public class ConfigReader {

    private static class CloseableYamlReader extends YamlReader implements AutoCloseable {

        public CloseableYamlReader(Reader reader) {
            super(reader);
        }
    }

    public static GameConfig load(String pathToConfig) {
        try (CloseableYamlReader reader = new CloseableYamlReader(new FileReader(pathToConfig))) {
            return reader.read(GameConfig.class);
        } catch (IOException e) {
            LoggerFactory.getLogger(ConfigReader.class)
                    .warn("Could not load the config file at: " + pathToConfig);
            return new GameConfig();
        }
    }

}
