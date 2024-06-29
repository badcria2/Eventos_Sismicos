public enum Columna {

    FECHA_UTC("FECHA_UTC",1), HORA_UTC("HORA_UTC",2),
    LATITUD("LATITUD",3), LONGITUD("LONGITUD",4), PROFUNDIDAD("PROFUNDIDAD",5), MAGNITUD("MAGNITUD",6);

    private String nombre;
    private int columna;

    private Columna (String nombre, int columna){
        this.nombre = nombre;
        this.columna = columna;
    }

    public String getNombre() {
        return nombre;
    }

    public int getColumna() {
        return columna;
    }
}