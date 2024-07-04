import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Archivo {
    private  Sheet sheet;
    public Archivo (String rutaArchivo) throws IOException, ParseException {
        FileInputStream fileInputStream = new FileInputStream(rutaArchivo);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheetAt(0);
        workbook.close();
        fileInputStream.close();
    }
    public List<EventoSismico> transformarFileEventoSismico() throws ParseException {
        List<EventoSismico> registros = new ArrayList<>();

            Sheet sheet = this.sheet;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Saltar la fila de encabezados
                }

                int id = (int) row.getCell(0).getNumericCellValue();
                String fechaUTCStr = getCellValueAsString(row.getCell(1));
                Date fechaUTC = convertirFechaDesdeExcel(fechaUTCStr);
                String horaUTC = getCellValueAsString(row.getCell(2));
                double latitud = row.getCell(3).getNumericCellValue();
                double longitud = row.getCell(4).getNumericCellValue();
                int profundidad = (int) row.getCell(5).getNumericCellValue();
                double magnitud = row.getCell(6).getNumericCellValue();
                String fechaCorte = getCellValueAsString(row.getCell(7));
                String anio = getCellValueAsString(row.getCell(1));

                EventoSismico registro = new EventoSismico(id, fechaUTC, horaUTC, latitud, longitud, profundidad, magnitud, fechaCorte);
                registros.add(registro);
            }

        return registros;
    }
    public static String getCellValueAsString(Cell cell) {
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
    public static Date convertirFechaDesdeExcel(String fechaExcel) throws ParseException {
        // Convertir la cadena "YYYYMMDD" a una fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
        return formatoFecha.parse(fechaExcel);
    }
}