package modelo;

import java.io.Serializable;

public class IncidenciaId implements Serializable {

    private Integer idIncidencia;
    private Integer idUser;
    private Integer categoriaIncidencia;

    public IncidenciaId(Integer idIncidencia, Integer idUser, Integer categoriaIncidencia) {
        this.idIncidencia = idIncidencia;
        this.idUser = idUser;
        this.categoriaIncidencia = categoriaIncidencia;
    }

    public IncidenciaId() {

    }


    public Integer getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(Integer idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getCategoriaIncidencia() {
        return categoriaIncidencia;
    }

    public void setCategoriaIncidencia(Integer categoriaIncidencia) {
        this.categoriaIncidencia = categoriaIncidencia;
    }
}
