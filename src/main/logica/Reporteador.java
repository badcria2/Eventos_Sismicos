package main.logica;

import main.entidad.EventoSismico;
import main.utilitario.LoggingConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Clase para generar reportes de eventos sísmicos.
 *
 * @autor [Tu Nombre]
 */
public class Reporteador {
    private static final java.util.logging.Logger logger = Logger.getLogger(Reporteador.class.getName());

    /**
     * Imprime un reporte de eventos sísmicos agrupados por mes en la consola.
     *
     * @param registros Lista de registros de eventos sísmicos.
     */
    public void imprimirReportePorMes(List<EventoSismico> registros, String tituloReporte) {
        Class<?> configClass = LoggingConfig.class;

        if (registros == null || registros.isEmpty()) {
            logger.info("La lista de registros está vacía o es nula");
            return;
        }

        // Agrupar registros por mes
        Map<Integer, Long> frecuenciasPorMes = registros.stream()
                .collect(Collectors.groupingBy(EventoSismico::getMes, Collectors.counting()));

        long totalEventos = registros.size();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String[] meses = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SETIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};

        // Imprimir el encabezado
        System.out.println(tituloReporte+"\n");
        System.out.printf("%-3s %-12s %-10s %-10s%n", "Nº", "MES", "FREC", "PORC");
        System.out.println("===================================");

        // Imprimir datos por mes
        for (int i = 1; i <= 12; i++) {
            long frecuencia = frecuenciasPorMes.getOrDefault(i, 0L);
            double porcentaje = (double) frecuencia / totalEventos * 100;

            System.out.printf("%02d %-12s %-10d %-10s%n", i, meses[i - 1], frecuencia, decimalFormat.format(porcentaje) + "%");
        }

        System.out.println("===================================");
        System.out.printf("%-14s  %-11d %-10s%n", "TOTAL", totalEventos, decimalFormat.format(100.00) + "%");
    }

    /**
     * Exporta un reporte de eventos sísmicos agrupados por mes a un archivo de texto.
     *
     * @param registros Lista de registros de eventos sísmicos.
     * @return El nombre del archivo generado.
     */
    public String exportarReportePorMes(List<EventoSismico> registros, String tituloReporte) {
        Class<?> configClass = LoggingConfig.class;

        if (registros == null || registros.isEmpty()) {
            logger.info("La lista de registros está vacía o es nula");
            return "No se generó ningún archivo";
        }

        String nombreArchivo = generarNombreArchivo();

        // Agrupar registros por mes
        Map<Integer, Long> frecuenciasPorMes = registros.stream()
                .collect(Collectors.groupingBy(EventoSismico::getMes, Collectors.counting()));

        long totalEventos = registros.size();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String[] meses = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SETIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};


        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.printf(tituloReporte+"\n");
            writer.printf("%-3s %-12s %-10s %-10s%n", "Nº", "MES", "FREC", "PORC");
            writer.println("===================================");

            for (int i = 1; i <= 12; i++) {
                long frecuencia = frecuenciasPorMes.getOrDefault(i, 0L);
                double porcentaje = (double) frecuencia / totalEventos * 100;

                writer.printf("%02d %-12s %-10d %-10s%n", i, meses[i - 1], frecuencia, decimalFormat.format(porcentaje) + "%");
            }

            writer.println("===================================");
            writer.printf("%-14s  %-11d %-10s%n", "TOTAL", totalEventos, decimalFormat.format(100.00) + "%");
        } catch (IOException e) {
            logger.warning("Error al escribir en el archivo: " + nombreArchivo +" "+  e);
            return "No se logró generar ningún archivo";
        }
        return nombreArchivo;
    }

    /**
     * Exporta un reporte de eventos sísmicos agrupados por hora a un archivo de texto.
     *
     * @param eventosPorHora Mapa de eventos sísmicos agrupados por hora.
     * @return El nombre del archivo generado.
     */
    public String exportarReportePorHora(Map<Integer, List<EventoSismico>> eventosPorHora, String tituloReporte) {
        Class<?> configClass = LoggingConfig.class;

        if (eventosPorHora == null || eventosPorHora.isEmpty()) {
            logger.warning("El mapa de eventos por hora está vacío o es nulo");
            return "No se generó ningún archivo";
        }

        String nombreArchivo = generarNombreArchivo();

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.println(tituloReporte+"\n");
            writer.printf("%-5s %-10s%n", "HORA", "EVENTOS");
            writer.println("====================");

            for (Map.Entry<Integer, List<EventoSismico>> entry : eventosPorHora.entrySet()) {
                int hora = entry.getKey();
                int cantidadEventos = entry.getValue().size();
                writer.printf("%02d    %-10d%n", hora, cantidadEventos);
            }

            writer.println("====================");
            writer.printf("%-10s %-10d%n", "TOTAL", eventosPorHora.values().stream().mapToInt(List::size).sum());
        } catch (IOException e) {
            logger.warning("Error al escribir en el archivo: " + nombreArchivo + " "+ e);
            return "No se logró generar ningún archivo";
        }

        return nombreArchivo;
    }

    /**
     * Genera un nombre de archivo basado en la fecha y hora actual.
     *
     * @return El nombre del archivo generado.
     */
    private String generarNombreArchivo() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return "reporte_" + now.format(formatter) + ".txt";
    }
}
