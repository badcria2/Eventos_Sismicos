import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventoSismico {
    private int id;
    private Date fechaUTC;
    private String horaUTC;
    private double latitud;
    private double longitud;
    private int profundidad;
    private double magnitud;
    private String fechaCorte;

    // Constructor
    public EventoSismico(int id, Date fechaUTC, String horaUTC, double latitud, double longitud, int profundidad, double magnitud, String fechaCorte) {
        this.id = id;
        this.fechaUTC = fechaUTC;
        this.horaUTC = horaUTC;
        this.latitud = latitud;
        this.longitud = longitud;
        this.profundidad = profundidad;
        this.magnitud = magnitud;
        this.fechaCorte = fechaCorte;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public Date getFechaUTC() {
        return fechaUTC;
    }

    public String getHoraUTC() {
        return horaUTC;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public double getMagnitud() {
        return magnitud;
    }

    public String getFechaCorte() {
        return fechaCorte;
    }

    @Override
    public String toString() {
        return "RegistroSismo{" +
                "id=" + id +
                ", fechaUTC='" + fechaUTC + '\'' +
                ", horaUTC='" + horaUTC + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", profundidad=" + profundidad +
                ", magnitud=" + magnitud +
                ", fechaCorte='" + fechaCorte + '\'' +
                '}';
    }

    // Métodos adicionales para obtener fechas en formato String
    public String getFechaUTCFormatted() {
        return new SimpleDateFormat("yyyyMMdd").format(fechaUTC);
    }

    public String getFechaCorteFormatted() {
        return new SimpleDateFormat("yyyyMMdd").format(fechaCorte);
    }

    // Métodos para obtener el año y mes de fechaUTC
    public int getAnio() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaUTC);
        return calendar.get(Calendar.YEAR);
    }

    public int getMes() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaUTC);
        return calendar.get(Calendar.MONTH) + 1; // Enero es 0 en Calendar, por eso se suma 1
    }

    public int getHora() {
        return Integer.parseInt(horaUTC.substring(0, 2));
    }
}
