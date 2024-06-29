import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Archivo {
    private  Sheet sheet;
    private int cantidadElementosEvaluar = 0;
    private  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    public Archivo (String rutaArchivo) throws IOException, ParseException {
        FileInputStream fileInputStream = new FileInputStream(rutaArchivo);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheetAt(0);
        workbook.close();
        fileInputStream.close();
    }
    public Map<Integer, Integer> getFrecuenciasPorMes(int anio, int columnaDato) throws ParseException {
        cantidadElementosEvaluar = 0;
        Map<Integer, Integer> frecuencias = new HashMap<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // Saltar la fila de encabezados
            }
            Cell dateCell = row.getCell(columnaDato);
            if (dateCell != null && dateCell.getCellType() == CellType.NUMERIC) {
                Date date = dateFormat.parse(String.valueOf((long) dateCell.getNumericCellValue()));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                if (year == anio) {
                    int month = calendar.get(Calendar.MONTH) + 1;
                    cantidadElementosEvaluar +=  1;
                    frecuencias.put(month, frecuencias.getOrDefault(month, 0) + 1);
                }
            }
        }

        return frecuencias;
    }
    public void imprimirFrecuencias(Map<Integer, Integer> frecuencias) {
        String[] meses = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SETIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // Ajuste del formato para mejor alineación
        System.out.printf("%-3s %-12s %-10s %-10s%n", "Nº", "MES", "FREC", "PORC");
        System.out.println("===================================");

        for (int i = 1; i <= 12; i++) {
            int frecuencia = frecuencias.getOrDefault(i, 0);
            double porcentaje = (double) frecuencia / cantidadElementosEvaluar * 100;

            System.out.printf("%02d %-12s %-10d %-10s%n", i, meses[i - 1], frecuencia, decimalFormat.format(porcentaje) + "%");
        }

        System.out.println("===================================");

        // Asegurar alineación correcta de "TOTAL" usando %s para las cadenas
        System.out.printf("%-14s  %-11s %-10s%n", "TOTAL", cantidadElementosEvaluar, decimalFormat.format(100.00) + "%");
    }



    public void exportarReporte(Map<Integer, Integer> frecuencias ) {
        String[] meses = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SETIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            writer.printf("%-3s %-12s %-10s %-10s%n", "Nº", "MES", "FREC", "PORC");
            writer.println("===================================");

            for (int i = 1; i <= 12; i++) {
                int frecuencia = frecuencias.getOrDefault(i, 0);
                double porcentaje = (double) frecuencia / cantidadElementosEvaluar * 100;

                writer.printf("%02d %-12s %-10d %-10s%n", i, meses[i - 1], frecuencia, decimalFormat.format(porcentaje) + "%");
            }

            writer.println("===================================");

            writer.printf("%-14s  %-11s %-10s%n", "TOTAL", cantidadElementosEvaluar, decimalFormat.format(100.00) + "%");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}