package modelo;



public class ObjectUser {

    String status;
    String desc;
    Usuario usuario;

    public ObjectUser(String status, String desc, Usuario usuario) {
        this.status = status;
        this.desc = desc;
        this.usuario = usuario;
    }

    public ObjectUser() {
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
