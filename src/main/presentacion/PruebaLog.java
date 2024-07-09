package main.presentacion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PruebaLog {
    private static final Logger logger = LogManager.getLogger(PruebaLog.class);

    public static void main(String[] args) {

        logger.info("Esto es una prueba de log.");
        logger.warn("Esto es un mensaje de error.");
        logger.debug("Esto es una prueba de log.");
    }
}
