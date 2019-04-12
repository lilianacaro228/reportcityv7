package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Categorias implements Serializable {

    private Integer idCategorias;

    private Date fechaCreacion;

    private Date fechaModificacion;

    private String nombreCategoria;

    private String descripcion;

    private Set<Incidencia> incidencias = new HashSet<Incidencia>(0);

    public Categorias(Integer idCategorias, Date fechaCreacion, Date fechaModificacion, String nombreCategoria, String descripcion, Set<Incidencia> incidencias) {
        this.idCategorias = idCategorias;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.nombreCategoria = nombreCategoria;
        this.descripcion = descripcion;
        this.incidencias = incidencias;
    }

    public Categorias() {
    }

    public Integer getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(Integer idCategorias) {
        this.idCategorias = idCategorias;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }
}
