package utilitario;

import java.util.Scanner;

/**
 * Clase utilitaria para la validación de entradas del usuario.
 *
 * @autor [Tu Nombre]
 */
public class Util {

    /**
     * Obtiene un año válido entre 1960 y 2024.
     *
     * @param scanner Scanner para la entrada del usuario.
     * @return Año válido.
     */
    public static int obtenerAnioValido(Scanner scanner) {
        return obtenerEnteroValido(scanner, "Ingrese año a consultar [1960-2024]: ", 1960, 2024);
    }

    /**
     * Obtiene un mes válido entre 1 y 12.
     *
     * @param scanner Scanner para la entrada del usuario.
     * @return Mes válido.
     */
    public static int obtenerMesValido(Scanner scanner) {
        return obtenerEnteroValido(scanner, "Ingrese mes a consultar [1-12]: ", 1, 12);
    }

    /**
     * Obtiene una magnitud válida mayor o igual a 0.
     *
     * @param scanner Scanner para la entrada del usuario.
     * @param tipoMagnitud Descripción del tipo de magnitud (mínima o máxima).
     * @return Magnitud válida.
     */
    public static double obtenerMagnitudValida(Scanner scanner, String tipoMagnitud) {
        return obtenerDoubleValido(scanner, "Ingrese magnitud " + tipoMagnitud + ": ", 0, Double.MAX_VALUE);
    }

    /**
     * Método genérico para obtener un entero válido dentro de un rango.
     *
     * @param scanner Scanner para la entrada del usuario.
     * @param mensaje Mensaje a mostrar al usuario.
     * @param min Valor mínimo permitido.
     * @param max Valor máximo permitido.
     * @return Entero válido dentro del rango.
     */
    private static int obtenerEnteroValido(Scanner scanner, String mensaje, int min, int max) {
        int valor = min - 1;
        while (valor < min || valor > max) {
            System.out.print(mensaje);
            if (scanner.hasNextInt()) {
                valor = scanner.nextInt();
                if (valor < min || valor > max) {
                    System.out.println("Valor fuera de rango. Intente nuevamente.");
                }
            } else {
                System.out.println("Entrada inválida. Intente nuevamente.");
                scanner.next(); // limpiar el buffer
            }
        }
        return valor;
    }

    /**
     * Método genérico para obtener un valor double válido dentro de un rango.
     *
     * @param scanner Scanner para la entrada del usuario.
     * @param mensaje Mensaje a mostrar al usuario.
     * @param min Valor mínimo permitido.
     * @param max Valor máximo permitido.
     * @return Valor double válido dentro del rango.
     */
    private static double obtenerDoubleValido(Scanner scanner, String mensaje, double min, double max) {
        double valor = min - 1;
        while (valor < min || valor > max) {
            System.out.print(mensaje);
            if (scanner.hasNextDouble()) {
                valor = scanner.nextDouble();
                if (valor < min || valor > max) {
                    System.out.println("Valor fuera de rango. Intente nuevamente.");
                }
            } else {
                System.out.println("Entrada inválida. Intente nuevamente.");
                scanner.next(); // limpiar el buffer
            }
        }
        return valor;
    }
}
