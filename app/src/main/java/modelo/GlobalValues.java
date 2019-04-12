package modelo;

import android.app.Application;

import java.util.List;

import utils.Utilitarios;

public class GlobalValues extends Application {

    private  Usuario usuarioActivo;
    private  List<Categorias>  categoriasGenerales;
    private  Incidencia incidenciaActiva;
    private  Utilitarios tools;
    private String modoIncidencia;


    public GlobalValues() {
    }

    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public void setUsuarioActivo(Usuario usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
    }

    public List<Categorias> getCategoriasGenerales() {
        return categoriasGenerales;
    }

    public void setCategoriasGenerales(List<Categorias> categoriasGenerales) {
        this.categoriasGenerales = categoriasGenerales;
    }

    public Incidencia getIncidenciaActiva() {
        return incidenciaActiva;
    }

    public void setIncidenciaActiva(Incidencia incidenciaActiva) {
        this.incidenciaActiva = incidenciaActiva;
    }

    public Utilitarios getTools() {
        return tools;
    }

    public void setTools(Utilitarios tools) {
        this.tools = tools;
    }

    public String getModoIncidencia() {
        return modoIncidencia;
    }

    public void setModoIncidencia(String modoIncidencia) {
        this.modoIncidencia = modoIncidencia;
    }
}
