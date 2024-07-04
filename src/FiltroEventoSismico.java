import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FiltroEventoSismico {
    public List<EventoSismico> filtrarPorRangoDeAnios(List<EventoSismico> registros, int anioInicio, int anioFin) {
        return registros.stream()
                .filter(registro -> {
                    int anio = registro.getAnio();
                    return anio >= anioInicio && anio <= anioFin;
                })
                .collect(Collectors.toList());
    }
    public List<EventoSismico> filtrarPorMesYRangoDeMagnitudes(List<EventoSismico> registros, int mes, double magnitudMin, double magnitudMax, int anio) {
        return registros.stream()
                .filter(registro -> registro.getMes() == mes &&
                        registro.getAnio() == anio &&
                        registro.getMagnitud() >= magnitudMin &&
                        registro.getMagnitud() <= magnitudMax)
                .collect(Collectors.toList());
    }
    public Map<Integer, List<EventoSismico>> filtrarPorHoraYAnio(List<EventoSismico> registros, int anio) {
        // Crear un mapa para agrupar registros por hora
        Map<Integer, List<EventoSismico>> registrosPorHora = new HashMap<>();

        // Filtrar los registros por año y agrupar por hora
        for (EventoSismico registro : registros) {
            if (registro.getAnio() == anio) {
                int hora = registro.getHora();
                registrosPorHora.putIfAbsent(hora, new ArrayList<>());
                registrosPorHora.get(hora).add(registro);
            }
        }

        return registrosPorHora;
    }
    public List<EventoSismico> filtrarPorMesDadoAnio(List<EventoSismico> registros, int anio) {
        return registros.stream()
                .filter(registro -> registro.getAnio() == anio)
                .collect(Collectors.toList());
    }
}
