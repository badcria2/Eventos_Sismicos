package main.utilitario;

import java.util.logging.LogManager;
import java.io.IOException;
import java.io.InputStream;

public class LoggingConfig {
    private static final String LOGGING_PROPERTIES_FILE = "\\resource\\logging.properties";

    static {
        // Cargar configuración de logging desde logging.properties
        try (InputStream configFile = LoggingConfig.class.getClassLoader().getResourceAsStream(LOGGING_PROPERTIES_FILE)) {
            if (configFile == null) {
                System.err.println("No se pudo encontrar el archivo de configuración " + LOGGING_PROPERTIES_FILE);
            } else {
                LogManager.getLogManager().readConfiguration(configFile);
             }
        } catch (IOException e) {
            System.err.println("Error al cargar la configuración de logging: " + e.getMessage());
        }
    }

    // Método init para asegurar que la clase se cargue
    public static void init() {
        // Método vacío que simplemente fuerza la carga de la clase
    }
}
