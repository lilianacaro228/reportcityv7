package modelo;



import java.util.HashSet;
import java.util.Set;

public class ObjectIncidencia {

    String status;
    String desc;
    Incidencia incidencia;
    Set<Incidencia> incidencias = new HashSet<Incidencia>(0);

    public ObjectIncidencia(String status, String desc, Incidencia incidencia, Set<Incidencia> incidencias) {
        this.status = status;
        this.desc = desc;
        this.incidencia = incidencia;
        this.incidencias = incidencias;
    }

    public ObjectIncidencia() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Incidencia getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }

    public Set<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }
}
