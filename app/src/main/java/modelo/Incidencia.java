package modelo;

import java.io.Serializable;
import java.util.Date;

public class Incidencia implements Serializable {

    private IncidenciaId id;
    private Categorias categorias;
    private Usuario usuario;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private String titulo;
    private byte[] multimedia;
    private String latitud;
    private String longitud;
    private String barrioSector;
    private String descripcionAdicional;
    private String estado;
    private String eliminado;

    public Incidencia(IncidenciaId id, Categorias categorias, Usuario usuario, Date fechaCreacion, Date fechaModificacion, String titulo, byte[] multimedia, String latitud, String longitud, String barrioSector, String descripcionAdicional, String estado, String eliminado) {
        this.id = id;
        this.categorias = categorias;
        this.usuario = usuario;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.titulo = titulo;
        this.multimedia = multimedia;
        this.latitud = latitud;
        this.longitud = longitud;
        this.barrioSector = barrioSector;
        this.descripcionAdicional = descripcionAdicional;
        this.estado = estado;
        this.eliminado = eliminado;
    }

    public Incidencia() {
    }

    public IncidenciaId getId() {
        return id;
    }

    public void setId(IncidenciaId id) {
        this.id = id;
    }

    public Categorias getCategorias() {
        return categorias;
    }

    public void setCategorias(Categorias categorias) {
        this.categorias = categorias;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public byte[] getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(byte[] multimedia) {
        this.multimedia = multimedia;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getBarrioSector() {
        return barrioSector;
    }

    public void setBarrioSector(String barrioSector) {
        this.barrioSector = barrioSector;
    }

    public String getDescripcionAdicional() {
        return descripcionAdicional;
    }

    public void setDescripcionAdicional(String descripcionAdicional) {
        this.descripcionAdicional = descripcionAdicional;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }
}
