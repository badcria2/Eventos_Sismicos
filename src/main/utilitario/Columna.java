package main.utilitario;

/**
 * Enumeración que representa las columnas de un archivo de eventos sísmicos.
 * Cada columna tiene un nombre y un número de columna asociado.
 *
 * @autor [Tu Nombre]
 */
public enum Columna {
    FECHA_UTC("FECHA_UTC", 1),
    HORA_UTC("HORA_UTC", 2),
    LATITUD("LATITUD", 3),
    LONGITUD("LONGITUD", 4),
    PROFUNDIDAD("PROFUNDIDAD", 5),
    MAGNITUD("MAGNITUD", 6);

    private final String nombre;
    private final int columna;

    /**
     * Constructor para la enumeración Columna.
     *
     * @param nombre Nombre de la columna.
     * @param columna Número de la columna.
     */
    Columna(String nombre, int columna) {
        this.nombre = nombre;
        this.columna = columna;
    }

    /**
     * Obtiene el nombre de la columna.
     *
     * @return El nombre de la columna.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el número de la columna.
     *
     * @return El número de la columna.
     */
    public int getColumna() {
        return columna;
    }
}
