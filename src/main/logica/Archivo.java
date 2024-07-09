package main.logica;

import main.entidad.EventoSismico;
import main.utilitario.LoggingConfig;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Clase para manejar la lectura y transformación de archivos Excel a objetos EventoSismico.
 *
 * @autor [Tu Nombre]
 */
public class Archivo {
    private static final java.util.logging.Logger logger = Logger.getLogger(Archivo.class.getName());
    private Sheet sheet;

    /**
     * Constructor que carga el archivo Excel y selecciona la primera hoja.
     *
     * @param rutaArchivo Ruta del archivo Excel.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public Archivo(String rutaArchivo) throws IOException {
        Class<?> configClass = LoggingConfig.class;

        try (FileInputStream fileInputStream = new FileInputStream(rutaArchivo);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            sheet = workbook.getSheetAt(0);
        } catch (IOException e) {
            logger.warning("Error al leer el archivo: " + rutaArchivo + " " + e);
            throw e; // Propaga la excepción para que sea manejada en un nivel superior
        }
    }

    /**
     * Transforma el contenido de la hoja seleccionada en una lista de objetos EventoSismico.
     *
     * @return Lista de eventos sísmicos.
     * @throws ParseException Si ocurre un error al parsear las fechas.
     */
    public List<EventoSismico> transformarFileEventoSismico() throws ParseException {
        Class<?> configClass = LoggingConfig.class;

        List<EventoSismico> registros = new ArrayList<>();
        try {
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Saltar la fila de encabezados
                }

                EventoSismico registro = transformarFilaAEventoSismico(row);
                registros.add(registro);
            }
        } catch (Exception e) {
            logger.warning("Error al transformar el archivo en lista de eventos sísmicos " + e);
            throw e; // Propaga la excepción para que sea manejada en un nivel superior
        }
        return registros;
    }

    /**
     * Transforma una fila del archivo Excel en un objeto EventoSismico.
     *
     * @param row Fila del archivo Excel.
     * @return Objeto EventoSismico.
     * @throws ParseException Si ocurre un error al parsear las fechas.
     */
    private EventoSismico transformarFilaAEventoSismico(Row row) throws ParseException {
        int id = (int) row.getCell(0).getNumericCellValue();
        String fechaUTCStr = getCellValueAsString(row.getCell(1));
        Date fechaUTC = convertirFechaDesdeExcel(fechaUTCStr);
        String horaUTC = getCellValueAsString(row.getCell(2));
        double latitud = row.getCell(3).getNumericCellValue();
        double longitud = row.getCell(4).getNumericCellValue();
        int profundidad = (int) row.getCell(5).getNumericCellValue();
        double magnitud = row.getCell(6).getNumericCellValue();
        String fechaCorte = getCellValueAsString(row.getCell(7));

        return new EventoSismico(id, fechaUTC, horaUTC, latitud, longitud, profundidad, magnitud, fechaCorte);
    }

    /**
     * Obtiene el valor de una celda como String.
     *
     * @param cell Celda del archivo Excel.
     * @return Valor de la celda como String.
     */
    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    return dateFormat.format(date);
                } else {
                    // Convertir el número a String sin notación científica
                    long numericValue = (long) cell.getNumericCellValue();
                    return String.valueOf(numericValue);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                // Evaluar la fórmula y devolver el resultado como cadena
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    /**
     * Convierte una fecha en formato "YYYYMMDD" a un objeto Date.
     *
     * @param fechaExcel Fecha en formato "YYYYMMDD".
     * @return Objeto Date.
     * @throws ParseException Si ocurre un error al parsear la fecha.
     */
    private static Date convertirFechaDesdeExcel(String fechaExcel) throws ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
        return formatoFecha.parse(fechaExcel);
    }
}
