package main.utilitario;

import java.util.logging.LogManager;
import java.io.IOException;
import java.io.InputStream;
public class LoggingConfig {
    private static final String LOGGING_PROPERTIES_FILE = "logging.properties";

    static {
        // Cargar configuración de logging desde logging.properties
        try (InputStream configFile = LoggingConfig.class.getClassLoader().getResourceAsStream(LOGGING_PROPERTIES_FILE)) {
            if (configFile == null) {
                System.err.println("No se pudo encontrar el archivo de configuración " + LOGGING_PROPERTIES_FILE);
            } else {
                LogManager.getLogManager().readConfiguration(configFile);
                System.out.println("Configuración de logging cargada correctamente.");
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la configuración de logging: " + e.getMessage());
        }
    }
}
