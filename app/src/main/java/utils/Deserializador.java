package utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import modelo.Categorias;
import modelo.Incidencia;
import modelo.IncidenciaId;
import modelo.ObjectCategoria;
import modelo.ObjectIncidencia;
import modelo.ObjectUser;
import modelo.Usuario;

public class Deserializador {


    JSONObject json =null;

    private static final String TAG = "Deserializador";

    //private static final Logger log = Logger.getLogger(Deserializador.class.getName());

    public ObjectUser getObjectUser(String response) throws JSONException {

        ObjectUser obj = new ObjectUser();

        json = new JSONObject( response );
        Log.i(TAG,"LLEGAS AQUI dESERIALZIADOD");

        try {

            obj.setStatus(json.getString("status"));
            obj.setDesc(json.getString("desc"));
            if( obj.getStatus().equals("OK")){

                obj.setUsuario(getUsuario(json.getJSONObject("usuario")));
            }


        }catch ( Exception ex){

            Log.i(TAG, ex.getLocalizedMessage());

            new Throwable( ex.getMessage() );

        }
        return obj;
    }



    public ObjectCategoria getObjectCategorias(String response) throws JSONException {

        ObjectCategoria obj = new ObjectCategoria();

        json = new JSONObject( response );

        try {

            obj.setStatus(json.getString("status"));
            obj.setDesc(json.getString("desc"));

            if( obj.getStatus().equals("OK")){

                obj.setCategorias(getLisCategorias(json.getJSONArray("categorias")));
            }


        }catch ( Exception ex){

            Log.i(TAG, ex.getLocalizedMessage());

            new Throwable( ex.getMessage() );

        }
        return obj;
    }


    public ObjectIncidencia getObjectIncidencias(String response) throws JSONException {

        ObjectIncidencia obj = new ObjectIncidencia();

        json = new JSONObject( response );

        try {

            obj.setStatus(json.getString("status"));
            obj.setDesc(json.getString("desc"));

            if( obj.getStatus().equals("OK")){


                obj.setIncidencias( getListIncidencias( json.getJSONArray("incidencias") ));
            }


        }catch ( Exception ex){

            Log.i(TAG, ex.getLocalizedMessage());

            new Throwable( ex.getMessage() );

        }
        return obj;
    }

    public Usuario getUsuario(JSONObject jsonUser) throws JSONException,Exception {


        Usuario user = new Usuario();

        try {

            user.setIdUser(jsonUser.getInt("idUser"));
            Log.i(TAG,"getUSUARIOS");

            user.setFechaCreacion(getDate2TimeStamp(jsonUser.getLong("fechaCreacion")));
            user.setFechaModificacion(getDate2TimeStamp(jsonUser.getLong("fechaModificacion")));

            user.setNombres(jsonUser.getString("nombres"));
            user.setApellidos(jsonUser.getString("apellidos"));

            user.setMail(jsonUser.getString("mail"));
           // user.setClave(jsonUser.getString("clave"));

            user.setIncidencias(getListIncidencias(jsonUser.getJSONArray("incidencias")));
        }catch (Exception ex){
            Log.i(TAG,"Lllegas error usuarios desderial: " + ex.getLocalizedMessage());

            new Throwable( ex);
        }

        return user;

    }//getUsuario




    public Set<Incidencia> getListIncidencias(JSONArray incidencias) throws Exception {

        Set<Incidencia> ListIncidencias = new HashSet<Incidencia>(0);

            Log.i(TAG,"Incidencias llegas deserializador: " + incidencias.length());

        for(int i=0;i < incidencias.length();i++) {

             JSONObject incidencia = incidencias.getJSONObject(i);

            ListIncidencias.add( getIncidencia( incidencia )  );

        }

        Log.i(TAG,"Incidencias Codificadas: " + ListIncidencias.size());

        return ListIncidencias;
    }



    public Incidencia getIncidencia(JSONObject jsonINC) throws JSONException,Exception{
        Incidencia incidencia = null;


            incidencia = new Incidencia();

           // json = new JSONObject( jsonINC );

            JSONObject IncidenciaIdOBJ = jsonINC.getJSONObject("id");

            IncidenciaId id = new IncidenciaId();



            id.setIdIncidencia( IncidenciaIdOBJ.getInt( "idIncidencia" ) );


            id.setIdUser(  IncidenciaIdOBJ.getInt( "idUser" ) );

            id.setCategoriaIncidencia( IncidenciaIdOBJ.getInt( "categoriaIncidencia" )  );

            incidencia.setId(id);

            incidencia.setFechaCreacion( getDate2TimeStamp(jsonINC.getLong("fechaCreacion") ) );

            incidencia.setFechaModificacion( getDate2TimeStamp(jsonINC.getLong("fechaModificacion") ) );



            incidencia.setLatitud(jsonINC.getString("latitud") );

            incidencia.setLongitud(jsonINC.getString("longitud") );

            incidencia.setBarrioSector(jsonINC.getString("barrioSector") );

            incidencia.setDescripcionAdicional(jsonINC.getString("descripcionAdicional") );

            incidencia.setEstado(jsonINC.getString("estado") );

            incidencia.setEliminado(jsonINC.getString("eliminado") );

            incidencia.setTitulo(jsonINC.getString("titulo") );



        return incidencia;

    }//getIncidencia

    public List<Categorias> getLisCategorias( JSONArray categoriasss) throws JSONException  {

        List<Categorias> categos = new ArrayList<Categorias>();

        try {

            for (int i = 0; i < categoriasss.length(); i++) {

                JSONObject categ = categoriasss.getJSONObject(i);

                categos.add(getCategoria(categ));

            }

        }catch (Exception ex ){

            Log.i( TAG,ex.getLocalizedMessage()  );
            new Throwable( ex.getMessage() );
        }

        return categos;
    }




    public Categorias getCategoria(JSONObject categoriaJson){
        Categorias categoria = null;

        try{

            categoria = new Categorias();

            categoria.setIdCategorias( categoriaJson.getInt("idCategorias") );
            categoria.setNombreCategoria(  categoriaJson.getString("nombreCategoria"));
            categoria.setDescripcion(  categoriaJson.getString("descripcion"));
            categoria.setFechaCreacion( getDate2TimeStamp( categoriaJson.getLong("fechaCreacion")) );
            categoria.setFechaModificacion( getDate2TimeStamp( categoriaJson.getLong("fechaModificacion"))  );



        }catch( Exception ex ){

            Log.i( TAG,ex.getLocalizedMessage()  );

            new Throwable( ex.getMessage() );

        }

        return categoria;

    }//getCategoria


    public Date getDate2TimeStamp(Long fechaLong ) throws ParseException {
        Calendar fd = Calendar.getInstance();
        fd.setTime( new Date(fechaLong) );

        fd.add(Calendar.HOUR, -5);

        // Date fecha = new Date(fechaLong);

        return fd.getTime();

    }//getDate2TimeStamp


}
