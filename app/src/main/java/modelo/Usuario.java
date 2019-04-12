package modelo;

import android.app.Application;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Usuario  implements Serializable   {

    private Integer idUser;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private String nombres;
    private String apellidos;
    private String mail;
    private String clave;
    private String usuarioActivo;
    private Set<Activacion> activacions = new HashSet<Activacion>(0);
    private Set<Incidencia> incidencias = new HashSet<Incidencia>(0);


    public Usuario(Integer idUser, Date fechaCreacion, Date fechaModificacion, String nombres, String apellidos, String mail, String clave, String usuarioActivo, Set activacions, Set incidencias) {
        this.idUser = idUser;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.mail = mail;
        this.clave = clave;
        this.usuarioActivo = usuarioActivo;
        this.activacions = activacions;
        this.incidencias = incidencias;
    }

    public Usuario() {
    }


    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getUsuarioActivo() {
        return usuarioActivo;
    }

    public void setUsuarioActivo(String usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
    }

    public Set getActivacions() {
        return activacions;
    }

    public void setActivacions(Set activacions) {
        this.activacions = activacions;
    }

    public Set getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(Set incidencias) {
        this.incidencias = incidencias;
    }
}
