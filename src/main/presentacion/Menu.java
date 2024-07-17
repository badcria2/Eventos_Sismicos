package main.presentacion;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import main.entidad.EventoSismico;
import main.logica.ArchivoEventoSismico;
import main.logica.FiltroEventoSismico;
import main.logica.Reporteador;
import main.utilitario.LoggingConfig;
import main.utilitario.Util;

public class Menu {
    private static final Logger logger = Logger.getLogger(Menu.class.getName());
    static String nombreArchivo = "C:\\Users\\reibi\\IdeaProjects\\Eventos_Sismicos\\src\\resource\\Catalogo1960_2023.xlsx";

    public void iniciarMenu() throws ParseException {
        try {
            ArchivoEventoSismico archivoEventoSismico = new ArchivoEventoSismico(nombreArchivo);
            menu(archivoEventoSismico);
        } catch (Exception ex) {
            logger.warning("Ha ocurrido un error: " + ex);
        }
    }

    private static void menu(ArchivoEventoSismico archivoEventoSismico) throws ParseException {
        Class<?> configClass = LoggingConfig.class;

        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = scanner.nextInt();
            List<EventoSismico> eventoSismicoList;
            try {
                eventoSismicoList = archivoEventoSismico.transformarFileEventoSismico();
            } catch (ParseException e) {
                logger.warning("Error al transformar archivo de eventos sísmicos: " + e);
                continue;
            }
            manejarOpcionMenu(opcion, eventoSismicoList, scanner);
        } while (opcion != 0);
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("--------------------------------------------------------");
        System.out.println("MENU PRINCIPAL");
        System.out.println("--------------------------------------------------------");
        System.out.println("1. Número de eventos sísmicos por año dado un rango de años.");
        System.out.println("2. Número de eventos sísmicos por mes dado un año.");
        System.out.println("3. Número de eventos sísmicos por mes dados un rango de magnitudes y un año");
        System.out.println("4. Número de eventos sísmicos por cada hora dado un año.");
        System.out.println("0. FIN DEL PROGRAMA");
        System.out.println("--------------------------------------------------------");
        System.out.print("Ingrese opción [0-4]: ");
    }

    private static void manejarOpcionMenu(int opcion, List<EventoSismico> eventoSismicoList, Scanner scanner) throws ParseException {
        switch (opcion) {
            case 1:
                eventoPorRangoAnios("MÓDULO 01 – EVENTOS POR RANGO DE AÑOS", eventoSismicoList, scanner);
                break;
            case 2:
                eventoPorMesDadoUnAnio("MÓDULO 02 – EVENTOS POR MES DADO UN AÑO", eventoSismicoList, scanner);
                break;
            case 3:
                eventoPorMagnitudesAnio("MÓDULO 03 – EVENTOS POR MES DADO UN RANGO DE MAGNITUDES Y UN AÑO", eventoSismicoList, scanner);
                break;
            case 4:
                eventoPorCadaHoraAnio("MÓDULO 04 – EVENTOS POR CADA HORA DADO UN AÑO", eventoSismicoList, scanner);
                break;
            case 0:
                System.out.println("Fin del programa.");
                break;
            default:
                System.out.println("Opción inválida. Intente nuevamente.");
        }
    }

    private static void eventoPorRangoAnios(String nombreModulo, List<EventoSismico> eventoSismicoList, Scanner scanner) {
        Reporteador reporteador = new Reporteador();
        int anioA = Util.obtenerAnioValido(scanner);
        int anioB = Util.obtenerAnioValido(scanner);
        FiltroEventoSismico filtroEventoSismico = new FiltroEventoSismico();
        List<EventoSismico> eventoSismicosFiltrado = filtroEventoSismico.filtrarPorRangoDeAnios(eventoSismicoList, anioA, anioB);
        String tituloReporte = "Reporte A: Tabla con el número de eventos sísmicos por rango de años.\n" +
                "(en este caso los datos corresponden al rango de años " + anioA + " - " + anioB + ")";
        manejarSubmenu(tituloReporte, nombreModulo, scanner, eventoSismicosFiltrado, reporteador);
    }

    private static void eventoPorMesDadoUnAnio(String nombreModulo, List<EventoSismico> eventoSismicoList, Scanner scanner) throws ParseException {
        int anio = Util.obtenerAnioValido(scanner);
        FiltroEventoSismico filtroEventoSismico = new FiltroEventoSismico();
        List<EventoSismico> eventoSismicosFiltrado = filtroEventoSismico.filtrarPorMesDadoAnio(eventoSismicoList, anio);
        String tituloReporte = "Reporte B: Tabla con el número de eventos sísmicos por mes dado un año.\n" +
                "(en este caso los datos corresponden al año " + anio + ")";
        manejarSubmenu(tituloReporte, nombreModulo, scanner, eventoSismicosFiltrado, new Reporteador());
    }

    private static void eventoPorMagnitudesAnio(String nombreModulo, List<EventoSismico> eventoSismicoList, Scanner scanner) {
        int anio = Util.obtenerAnioValido(scanner);
        int mes = Util.obtenerMesValido(scanner);
        double magnitudMin = Util.obtenerMagnitudValida(scanner, "mínima");
        double magnitudMax = Util.obtenerMagnitudValida(scanner, "máxima");
        String tituloReporte = "Reporte C: Tabla con el número de eventos sísmicos por mes dado un rango de magnitudes y un año.\n" +
                "(en este caso los datos corresponden al año " + anio + ", mes" + mes + ", magnitud mínima " + magnitudMin + ", magnitud máxima " + magnitudMax + ")";
        List<EventoSismico> eventoSismicosFiltrado = new FiltroEventoSismico().filtrarPorMesYRangoDeMagnitudes(eventoSismicoList, mes, magnitudMin, magnitudMax, anio);
        manejarSubmenu(tituloReporte, nombreModulo, scanner, eventoSismicosFiltrado, new Reporteador());
    }

    private static void eventoPorCadaHoraAnio(String nombreModulo, List<EventoSismico> eventoSismicoList, Scanner scanner) {
        int anio = Util.obtenerAnioValido(scanner);
        Map<Integer, List<EventoSismico>> eventosPorHora = new FiltroEventoSismico().filtrarPorHoraYAnio(eventoSismicoList, anio);
        Reporteador reporteador = new Reporteador();
        String tituloReporte = "Reporte D: Tabla con el número de eventos sísmicos por cada hora dado un año.\n" +
                "(en este caso los datos corresponden al año " + anio + ")";
        manejarSubmenuHora(tituloReporte, nombreModulo, scanner, eventosPorHora, reporteador);
    }

    private static void manejarSubmenu(String tituloReporte, String nombreModulo, Scanner scanner, List<EventoSismico> eventoSismicosFiltrado, Reporteador reporteador) {
        int opcion;
        do {
            mostrarSubmenu(nombreModulo);
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Imprimiendo por pantalla...");
                    reporteador.imprimirReportePorMes(eventoSismicosFiltrado, tituloReporte);
                    break;
                case 2:
                    System.out.println("Exportando a archivo plano...");
                    String nombreArchivo = reporteador.exportarReportePorMes(eventoSismicosFiltrado, tituloReporte);
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

    private static void manejarSubmenuHora(String tituloReporte, String nombreModulo, Scanner scanner, Map<Integer, List<EventoSismico>> eventosPorHora, Reporteador reporteador) {
        int opcion;
        do {
            mostrarSubmenu(nombreModulo);
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Imprimiendo por pantalla...");
                    for (Map.Entry<Integer, List<EventoSismico>> entry : eventosPorHora.entrySet()) {
                        int hora = entry.getKey();
                        List<EventoSismico> eventos = entry.getValue();
                        System.out.printf("Hora: %02d, Eventos: %d%n", hora, eventos.size());
                    }
                    break;
                case 2:
                    System.out.println("Exportando a archivo plano...");
                    String nombreArchivo = reporteador.exportarReportePorHora(eventosPorHora, tituloReporte);
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

    private static void mostrarSubmenu(String nombreModulo) {
        System.out.println("--------------------------------------------------------");
        System.out.println(nombreModulo);
        System.out.println("--------------------------------------------------------");
        System.out.println("1. Imprimir por pantalla.");
        System.out.println("2. Exportar a archivo plano.");
        System.out.println("0. Volver al Menú Principal");
        System.out.println("--------------------------------------------------------");
        System.out.print("Ingrese opción [0-2]: ");
    }
}
