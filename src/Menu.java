import java.text.ParseException;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    static String nombreArchivo = "C:\\Users\\reibi\\IdeaProjects\\Eventos_Sismicos\\resource\\Catalogo1960_2023.xlsx";

    public static void main(String[] args) {
        try {
            Archivo archivo =  new Archivo(nombreArchivo);
            menu(archivo);
        }catch (Exception ex){
            System.err.println("Ha ocurrido un error: "+ ex.getMessage());
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

            switch (opcion) {
                case 1:
                    eventoPorRangoAnios("MÓDULO 01 – EVENTOS POR RANGO DE AÑOS",archivo);
                    break;
                case 2:
                    eventoPorMesDadoUnAnio("MÓDULO 02 – EVENTOS POR MES DADO UN AÑO",archivo);
                    break;
                case 3:
                    eventoPorMagnitudesAnio();
                    break;
                case 4:
                    eventoPorCadaHoraAnio();
                    break;
                case 0:
                    System.out.println("Fin del programa.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion!= 0);
    }

    
    private static void eventoPorRangoAnios(String nombreModulo, Archivo archivo) {
        Scanner scanner = new Scanner(System.in);
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
                    // Aquí va el código para imprimir por pantalla
                    break;
                case 2:
                    System.out.println("Exportando a archivo plano...");
                    // Aquí va el código para exportar a archivo plano
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion!= 0);
    }

    private static void eventoPorMesDadoUnAnio(String nombreModulo, Archivo archivo) throws ParseException {
        Scanner scanner = new Scanner(System.in);

        // aca validar el año a ingresar, solo debe estar dentro de ese rango
        System.out.print("Ingrese año a cosultar [1960-2024]: ");
        int anio = scanner.nextInt();
        Map<Integer, Integer> frecuencias = archivo.getFrecuenciasPorMes(anio, Columna.FECHA_UTC.getColumna());

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
                    archivo.imprimirFrecuencias(frecuencias);
                    break;
                case 2:
                    System.out.println("Exportando a archivo plano...");
                    archivo.exportarReporte(frecuencias);
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion!= 0);
    }

    private static void eventoPorMagnitudesAnio() {
        // Aquí va el código para el módulo 3
        System.out.println("Módulo 3");
    }
    private static void eventoPorCadaHoraAnio() {
        // Aquí va el código para el módulo 4
        System.out.println("Módulo 4");
    }

}