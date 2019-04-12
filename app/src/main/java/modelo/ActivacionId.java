package modelo;

import java.io.Serializable;

public class ActivacionId implements Serializable {

    private int idActivacion;
    private int userId;

    public ActivacionId(int idActivacion, int userId) {
        this.idActivacion = idActivacion;
        this.userId = userId;
    }

    public ActivacionId() {
    }

    public int getIdActivacion() {
        return idActivacion;
    }

    public void setIdActivacion(int idActivacion) {
        this.idActivacion = idActivacion;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
