package Bean;

import android.util.Log;

import Servicio.ServiceProvider;
import modelo.ObjectIncidencia;
import modelo.ObjectUser;
import modelo.Usuario;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.Deserializador;
import utils.Serializador;

public class BeanServiceProvider {

    //private String serviceURL = "http://localhost:8084/api-city/api-rest/";
    //private String serviceURL = "http://reportcity.org/api-cityv2/api-rest/";
    private String serviceURL = "http://report-city.org/api-cityv3/api-rest/";

    private Retrofit retrofit;

    private Serializador serial;

    private Deserializador deserializador;

    private Usuario user = new Usuario();

    private static  ObjectUser obj = new ObjectUser();

    private static final String TAG = "BeanServiceProvider";

    private ServiceProvider cliente;








    public  BeanServiceProvider() {


    }



    public void iniciaRetrofit(){

        retrofit = new Retrofit.Builder()
                .baseUrl( serviceURL )
                .addConverterFactory( ScalarsConverterFactory.create() )
                .build();

        cliente = retrofit.create(ServiceProvider.class);

        serial = new Serializador();

        deserializador = new Deserializador();
    }//end iniciaRetrofit


    public Call<String> getCliente( String modo, String datos ){

        Call<String> call = null;

        switch (modo){

            case "GET": call = cliente.loginUsuario( datos );
                break;

            case "POST": call = cliente.updUsuario( datos );
                break;

            case "PUT": call = cliente.newUsuario( datos);
                break;

             default: call = null;

        }

        return call;


    }//end getCliente





    public ObjectUser getResponseUser( String responseBody) throws Exception {

        ObjectUser obj = deserializador.getObjectUser(responseBody);
        Log.i(TAG,"LLEGAS ACAQUI");
        if (!obj.getStatus().equals("OK")) {

            throw new Exception(obj.getDesc());
        }

        return obj;
    }












}