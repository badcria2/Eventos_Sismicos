package main.presentacion;

import main.utilitario.LoggingConfig;

import java.util.logging.Logger;

public class PruebaLog {
    private static final Logger logger = Logger.getLogger(PruebaLog.class.getName());

    public static void main(String[] args) {
        LoggingConfig.init();
        logger.info("Esto es una prueba de log.");
        logger.warning("Esto es un mensaje de advertencia.");
        logger.fine("Esto es una prueba de log a nivel FINE.");
    }
}
