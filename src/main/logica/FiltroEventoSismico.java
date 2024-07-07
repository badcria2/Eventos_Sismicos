package logica;

import entidad.EventoSismico;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase para filtrar registros de eventos sísmicos basados en varios criterios.
 *
 * @author [Tu Nombre]
 */
public class FiltroEventoSismico {

    /**
     * Filtra los registros de eventos sísmicos por un rango de años.
     *
     * @param registros Lista de registros de eventos sísmicos.
     * @param anioInicio Año de inicio del rango.
     * @param anioFin Año de fin del rango.
     * @return Lista de eventos sísmicos dentro del rango de años.
     */
    public List<EventoSismico> filtrarPorRangoDeAnios(List<EventoSismico> registros, int anioInicio, int anioFin) {
        if (anioInicio > anioFin) {
            throw new IllegalArgumentException("El año de inicio no puede ser mayor que el año de fin");
        }
        return registros.stream()
                .filter(registro -> {
                    int anio = registro.getAnio();
                    return anio >= anioInicio && anio <= anioFin;
                })
                .collect(Collectors.toList());
    }

    /**
     * Filtra los registros de eventos sísmicos por mes, rango de magnitudes y año.
     *
     * @param registros Lista de registros de eventos sísmicos.
     * @param mes Mes del año (1-12).
     * @param magnitudMin Magnitud mínima.
     * @param magnitudMax Magnitud máxima.
     * @param anio Año del evento.
     * @return Lista de eventos sísmicos que cumplen con los criterios.
     */
    public List<EventoSismico> filtrarPorMesYRangoDeMagnitudes(List<EventoSismico> registros, int mes, double magnitudMin, double magnitudMax, int anio) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }
        if (magnitudMin > magnitudMax) {
            throw new IllegalArgumentException("La magnitud mínima no puede ser mayor que la magnitud máxima");
        }
        return registros.stream()
                .filter(registro -> registro.getMes() == mes &&
                        registro.getAnio() == anio &&
                        registro.getMagnitud() >= magnitudMin &&
                        registro.getMagnitud() <= magnitudMax)
                .collect(Collectors.toList());
    }

    /**
     * Filtra y agrupa los registros de eventos sísmicos por hora en un año específico.
     *
     * @param registros Lista de registros de eventos sísmicos.
     * @param anio Año del evento.
     * @return Mapa donde la clave es la hora y el valor es la lista de eventos sísmicos en esa hora.
     */
    public Map<Integer, List<EventoSismico>> filtrarPorHoraYAnio(List<EventoSismico> registros, int anio) {
        return registros.stream()
                .filter(registro -> registro.getAnio() == anio)
                .collect(Collectors.groupingBy(EventoSismico::getHora));
    }

    /**
     * Filtra los registros de eventos sísmicos por un año específico.
     *
     * @param registros Lista de registros de eventos sísmicos.
     * @param anio Año del evento.
     * @return Lista de eventos sísmicos en el año especificado.
     */
    public List<EventoSismico> filtrarPorMesDadoAnio(List<EventoSismico> registros, int anio) {
        return registros.stream()
                .filter(registro -> registro.getAnio() == anio)
                .collect(Collectors.toList());
    }
}
