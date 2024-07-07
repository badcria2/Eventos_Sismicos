package presentacion;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entidad.EventoSismico;
import logica.Archivo;
import logica.FiltroEventoSismico;
import logica.Reporteador;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilitario.Util;

public class Menu {
    private static final Logger logger = LogManager.getLogger(Menu.class);

    static String nombreArchivo = "C:\\Users\\reibi\\IdeaProjects\\Eventos_Sismicos\\resource\\Catalogo1960_2023.xlsx";

    public static void main(String[] args) {
        try {
            Archivo archivo =  new Archivo(nombreArchivo);
            menu(archivo);
        }catch (Exception ex){
            logger.error("Ha ocurrido un error: ", ex);
        }
    }
    private static void menu(Archivo archivo) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("--------------------------------------------------------");
            System.out.println("MENU PRINCIPAL");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Número de eventos sísmicos por año dado un rango de años.");
            System.out.println("2. Número de eventos sísmicos por mes dado un año.");
            System.out.println("3. Número de eventos sísmicos por mes dados un rango de magnitudes y un año");
            System.out.println("4. Número de eventos sísmicos por cada hora dado un año.");
            System.out.println("0. FIN DEL PROGRAMA");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [1-4]: ");
            opcion = scanner.nextInt();
            List<EventoSismico> eventoSismicoList;
            try {
                eventoSismicoList = archivo.transformarFileEventoSismico();
            } catch (ParseException e) {
                logger.error("Error al transformar archivo de eventos sísmicos: ", e);
                continue;
            }
            switch (opcion) {
                case 1:
                    eventoPorRangoAnios("MÓDULO 01 – EVENTOS POR RANGO DE AÑOS",eventoSismicoList);
                    break;
                case 2:
                    eventoPorMesDadoUnAnio("MÓDULO 02 – EVENTOS POR MES DADO UN AÑO",eventoSismicoList);
                    break;
                case 3:
                    eventoPorMagnitudesAnio("MÓDULO 03 – EVENTOS POR MES DADO UN RANGO DE MAGNITUDES Y UN AÑO",eventoSismicoList);
                    break;
                case 4:
                    eventoPorCadaHoraAnio("MÓDULO 04 – EVENTOS POR CADA HORA DADO UN AÑO",eventoSismicoList);
                    break;
                case 0:
                    System.out.println("Fin del programa.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion!= 0);
    }

    
    private static void eventoPorRangoAnios(String nombreModulo, List<EventoSismico> eventoSismicoList) {
        Scanner scanner = new Scanner(System.in);
        Reporteador reporteador = new Reporteador();
        int opcion;
        int anioA = Util.obtenerAnioValido(scanner);
        int anioB = Util.obtenerAnioValido(scanner);
        FiltroEventoSismico filtroEventoSismico =  new FiltroEventoSismico();
        List<EventoSismico> eventoSismicosFiltrado = filtroEventoSismico.filtrarPorRangoDeAnios(eventoSismicoList,anioA, anioB);

        do {
            System.out.println("--------------------------------------------------------");
            System.out.println(nombreModulo);
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Imprimir por pantalla.");
            System.out.println("2. Exportar a archivo plano.");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [1-2]: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Imprimiendo por pantalla...");
                    reporteador.imprimirReportePorMes(eventoSismicosFiltrado);
                    break;
                case 2:
                    System.out.println("Exportando a archivo plano...");
                    String nombreArchivo = reporteador.exportarReportePorMes(eventoSismicosFiltrado);
                    System.out.println("El archivo: "+nombreArchivo+" se logró exportar el archivo exitosamente.");
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion!= 0);
    }

    private static void eventoPorMesDadoUnAnio(String nombreModulo, List<EventoSismico> eventoSismicoList) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int anio = Util.obtenerAnioValido(scanner);
        FiltroEventoSismico filtroEventoSismico =  new FiltroEventoSismico();
        List<EventoSismico> eventoSismicosFiltrado = filtroEventoSismico.filtrarPorMesDadoAnio(eventoSismicoList, anio);

        int opcion;

        do {
            System.out.println("--------------------------------------------------------");
            System.out.println(nombreModulo);
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Imprimir por pantalla.");
            System.out.println("2. Exportar a archivo plano.");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [1-2]: ");
            opcion = scanner.nextInt();
            Reporteador reporteador =  new Reporteador();

            switch (opcion) {
                case 1:
                    System.out.println("Imprimiendo por pantalla...");
                    reporteador.imprimirReportePorMes(eventoSismicosFiltrado);
                    break;
                case 2:
                    System.out.println("Exportando a archivo plano...");
                    String nombreArchivo = reporteador.exportarReportePorMes(eventoSismicosFiltrado);
                    System.out.println("El archivo: "+nombreArchivo+" se logró exportar el archivo exitosamente.");

                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion!= 0);
    }

    private static void eventoPorMagnitudesAnio(String nombreModulo, List<EventoSismico> eventoSismicoList) {
        Scanner scanner = new Scanner(System.in);
        int anio = Util.obtenerAnioValido(scanner);
        int mes = Util.obtenerMesValido(scanner);
        double magnitudMin = Util.obtenerMagnitudValida(scanner, "mínima");
        double magnitudMax = Util.obtenerMagnitudValida(scanner, "máxima");

        List<EventoSismico> eventoSismicosFiltrado = new FiltroEventoSismico().filtrarPorMesYRangoDeMagnitudes(eventoSismicoList, mes, magnitudMin, magnitudMax, anio);
        Reporteador reporteador = new Reporteador();

        int opcion;
        do {
            System.out.println("--------------------------------------------------------");
            System.out.println(nombreModulo);
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Imprimir por pantalla.");
            System.out.println("2. Exportar a archivo plano.");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [1-2]: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Imprimiendo por pantalla...");
                    reporteador.imprimirReportePorMes(eventoSismicosFiltrado);
                    break;
                case 2:
                    System.out.println("Exportando a archivo plano...");
                    String nombreArchivo = reporteador.exportarReportePorMes(eventoSismicosFiltrado);
                    System.out.println("El archivo: "+nombreArchivo+" se logró exportar el archivo exitosamente.");

                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private static void eventoPorCadaHoraAnio(String nombreModulo, List<EventoSismico> eventoSismicoList) {
        Scanner scanner = new Scanner(System.in);
        int anio = Util.obtenerAnioValido(scanner);
        Map<Integer, List<EventoSismico>> eventosPorHora = new FiltroEventoSismico().filtrarPorHoraYAnio(eventoSismicoList, anio);
        Reporteador reporteador = new Reporteador();

        int opcion;
        do {
            System.out.println("--------------------------------------------------------");
            System.out.println(nombreModulo);
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Imprimir por pantalla.");
            System.out.println("2. Exportar a archivo plano.");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("--------------------------------------------------------");
            System.out.print("Ingrese opción [0-2]: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Imprimiendo por pantalla...");
                    // Lógica para imprimir datos agrupados por hora
                    for (Map.Entry<Integer, List<EventoSismico>> entry : eventosPorHora.entrySet()) {
                        int hora = entry.getKey();
                        List<EventoSismico> eventos = entry.getValue();
                        System.out.printf("Hora: %02d, Eventos: %d%n", hora, eventos.size());
                    }
                    break;
                case 2:
                    System.out.println("Exportando a archivo plano...");
                    String nombreArchivo = reporteador.exportarReportePorHora(eventosPorHora);
                    System.out.println("El archivo: " + nombreArchivo + " se logró exportar exitosamente.");
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

}