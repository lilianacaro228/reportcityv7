package utils;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import modelo.Activacion;
import modelo.Categorias;
import modelo.Incidencia;
import modelo.Usuario;

public class Serializador implements Serializable {

    private static String TAG = "Serializador";



    public JSONArray serialCategorias( List<Categorias> categorias) throws JSONException,Exception {

        JSONArray objcategorias = null;

        if( categorias.size() > 0 && categorias != null){
            objcategorias = new JSONArray();
            for(Categorias categoria : categorias ){

                JSONObject categoriasObj = serialCategoria( categoria);

                objcategorias.put(categoriasObj);

            }
            //obj.put("incidencias",incidencias);
        }


        return objcategorias;

    }


    public JSONArray serialListIndicencias( Set<Incidencia> incidencias )  throws JSONException{

        JSONArray objIncidencias = new JSONArray();

        // if( incidencias.size() != 0 && incidencias != null){
        try{
            for(Incidencia incidencia : incidencias ){

                JSONObject incidenObj = serialIncidencia(incidencia);

                objIncidencias.put(incidenObj);

            }
            //obj.put("incidencias",incidencias);
            // }
        }catch( Exception ex ){

            Log.i(TAG,"Error serialListIndicencias: " + ex.getMessage());
        }

        return objIncidencias;

    }


    public JSONArray serialListActivaciones( Set<Activacion> activaciones ) throws JSONException,Exception{

        JSONArray objActivaciones = null;

        if( activaciones.size() >0 || activaciones != null){

            objActivaciones = new JSONArray();
            for(Activacion activacion : activaciones ){

                JSONObject incidenObj = serialActivacion( activacion );

                objActivaciones.put(incidenObj);

            }
            //obj.put("incidencias",incidencias);
        }


        return objActivaciones;

    }


    public JSONObject serialUsuario( Usuario user ) throws JSONException,Exception {

        JSONObject obj = new JSONObject();


            obj.put("idUser", user.getIdUser());
            obj.put("fechaCreacion", getformatDate(user.getFechaCreacion()));
            obj.put("fechaModificacion", getformatDate(user.getFechaModificacion()));

            obj.put("nombres", user.getNombres());
            obj.put("apellidos", user.getApellidos());

            obj.put("mail", user.getMail());
            obj.put("clave",user.getClave());
            obj.put("usuarioActivo", user.getUsuarioActivo());

            //Serial Incidencias
            obj.put("incidencias", serialListIndicencias(user.getIncidencias()));


        return obj;
    }

    public JSONObject serialCategoria( Categorias categoria ) throws JSONException,Exception {

        JSONObject obj = new JSONObject();

        obj.put("idCategorias",categoria.getIdCategorias());
        obj.put("fechaCreacion", getformatDate( categoria.getFechaCreacion() ));
        obj.put("fechaModificacion", getformatDate( categoria.getFechaModificacion() ) );
        obj.put("nombreCategoria",categoria.getNombreCategoria());
        obj.put("descripcion",categoria.getDescripcion());


        return obj;
    }

    public JSONObject serialIncidencia(Incidencia incidencia) throws JSONException ,Exception{

        JSONObject objIncidencia = new JSONObject();
        JSONObject objId = new JSONObject();

        try {
            objId.put("idIncidencia", incidencia.getId().getIdIncidencia());
            // objId.put("idUser",  incidencia.getUsuario().getIdUser() );
            objId.put("idUser", incidencia.getId().getIdUser());
           // objId.put("categoriaIncidencia", incidencia.getCategorias().getIdCategorias());
            objId.put("categoriaIncidencia", incidencia.getId().getCategoriaIncidencia());

            objIncidencia.put("id", objId);

            //objIncidencia.put("categorias", serialCategoria(incidencia.getCategorias()) );

            //objIncidencia.put("usuario", serialUsuario(incidencia.getUsuario()) );

            objIncidencia.put("fechaCreacion", getformatDate(incidencia.getFechaCreacion()));
            objIncidencia.put("fechaModificacion", getformatDate(incidencia.getFechaModificacion()));
            objIncidencia.put("titulo", incidencia.getTitulo());
            //objIncidencia.put("multimedia",bytes2String ( incidencia.getMultimedia() ));
            objIncidencia.put("latitud", incidencia.getLatitud());
            objIncidencia.put("longitud", incidencia.getLongitud());
            objIncidencia.put("barrioSector", incidencia.getBarrioSector());
            objIncidencia.put("descripcionAdicional", incidencia.getDescripcionAdicional());
            objIncidencia.put("estado", incidencia.getEstado());
            objIncidencia.put("eliminado", incidencia.getEliminado());

        }catch (Exception ex ){

            Log.i(TAG,ex.getLocalizedMessage());
            new Throwable(ex);
        }



        return objIncidencia;
    }



    public JSONObject serialActivacion(Activacion activacion) throws JSONException,Exception {
        JSONObject objAct = new JSONObject();

        objAct.put("idActivacion",activacion.getId().getIdActivacion());
        objAct.put("userId",activacion.getId().getUserId());
        objAct.put("tokenActivacion",activacion.getTokenActivacion());
        objAct.put("fechaCreacion",getformatDate( activacion.getFechaCreacion() ));
        objAct.put("fechaCaducidad",getformatDate( activacion.getFechaCaducidad() ));

        return objAct;
    }


    public long getformatDate(Date date ) throws Exception{

       // String jsonString = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date);

        return new Timestamp( date.getTime()).getTime();


    }
}
