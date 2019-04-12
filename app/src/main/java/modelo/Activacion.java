package modelo;

import java.io.Serializable;
import java.util.Date;

public class Activacion implements Serializable {

    private ActivacionId id;
    private Usuario usuario;
    private String tokenActivacion;
    private Date fechaCreacion;
    private Date fechaCaducidad;

    public Activacion(ActivacionId id, Usuario usuario, String tokenActivacion, Date fechaCreacion, Date fechaCaducidad) {
        this.id = id;
        this.usuario = usuario;
        this.tokenActivacion = tokenActivacion;
        this.fechaCreacion = fechaCreacion;
        this.fechaCaducidad = fechaCaducidad;
    }

    public Activacion() {
    }

    public ActivacionId getId() {
        return id;
    }

    public void setId(ActivacionId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTokenActivacion() {
        return tokenActivacion;
    }

    public void setTokenActivacion(String tokenActivacion) {
        this.tokenActivacion = tokenActivacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }
}
