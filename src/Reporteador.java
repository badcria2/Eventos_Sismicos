import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Reporteador {

    public void imprimirReportePorMes(List<EventoSismico> registros) {
        // Agrupar registros por mes
        Map<Integer, Long> frecuenciasPorMes = registros.stream()
                .collect(Collectors.groupingBy(EventoSismico::getMes, Collectors.counting()));

        long totalEventos = registros.size();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String[] meses = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SETIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};

        // Imprimir el encabezado
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

    public void exportarReportePorMes(List<EventoSismico> registros) {
        // Agrupar registros por mes
        Map<Integer, Long> frecuenciasPorMes = registros.stream()
                .collect(Collectors.groupingBy(EventoSismico::getMes, Collectors.counting()));

        long totalEventos = registros.size();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String[] meses = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SETIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};

        try (PrintWriter writer = new PrintWriter(new FileWriter("reporte_mes.txt"))) {
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
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}