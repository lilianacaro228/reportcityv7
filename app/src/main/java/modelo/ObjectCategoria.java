package modelo;



import android.app.Application;

import java.io.Serializable;
import java.util.List;

public class ObjectCategoria  implements Serializable {

    String status;
    String desc;
    Categorias categoria;
    List<Categorias> categorias;

    public ObjectCategoria() {
    }

    public ObjectCategoria(String status, String desc, Categorias categoria, List<Categorias> categorias) {
        this.status = status;
        this.desc = desc;
        this.categoria = categoria;
        this.categorias = categorias;
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

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public List<Categorias> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categorias> categorias) {
        this.categorias = categorias;
    }
}
