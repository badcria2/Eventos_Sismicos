package main.presentacion;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.io.IOException;
import java.io.InputStream;

public class PruebaLog {
    private static final Logger logger = Logger.getLogger(PruebaLog.class.getName());

    public static void main(String[] args) {
        // Cargar configuración de logging desde logging.properties
        try (InputStream configFile = PruebaLog.class.getClassLoader().getResourceAsStream("\\resource\\logging.properties")) {
            if (configFile == null) {
                System.err.println("No se pudo encontrar el archivo de configuración logging.properties");
                return;
            }
            LogManager.getLogManager().readConfiguration(configFile);
        } catch (IOException e) {
            System.err.println("Error al cargar la configuración de logging: " + e.getMessage());
        }

        logger.info("Esto es una prueba de log.");
        logger.warning("Esto es un mensaje de advertencia.");
        logger.fine("Esto es una prueba de log a nivel FINE.");
    }
}
