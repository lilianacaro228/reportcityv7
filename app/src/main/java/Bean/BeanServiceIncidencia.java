package Bean;

import Servicio.ServiceIncidencia;
import Servicio.ServiceProvider;
import modelo.Incidencia;
import modelo.ObjectIncidencia;
import modelo.ObjectUser;
import modelo.Usuario;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.Deserializador;
import utils.Serializador;

public class BeanServiceIncidencia {

    //private String serviceURL = "http://reportcity.org/api-cityv2/api-rest/";
    private String serviceURL = "http://report-city.org/api-cityv3/api-rest/";

    private Retrofit retrofit;

    private Serializador serial;

    private Deserializador deserializador;

    private Incidencia incidencia = new Incidencia();

    private static ObjectIncidencia obj = new ObjectIncidencia();

    private static final String TAG = "BeanServiceIncidencia";

    private ServiceIncidencia cliente;


    public BeanServiceIncidencia() {


    }


    public void iniciaRetrofit(){

        retrofit = new Retrofit.Builder()
                .baseUrl( serviceURL )
                .addConverterFactory( ScalarsConverterFactory.create() )
                .build();

        cliente = retrofit.create(ServiceIncidencia.class);

        serial = new Serializador();

        deserializador = new Deserializador();
    }//end iniciaRetrofit


    public Call<String> getCliente(String modo, String datos ){

        Call<String> call = null;

        switch (modo){

            case "GET": call = cliente.getIncidencias( datos );
                break;

            case "POST": call = cliente.updIncidencia( datos );
                break;

            case "PUT": call = cliente.newIncidencia( datos);
                break;

            default: call = null;

        }

        return call;


    }//end getCliente


    public ObjectIncidencia getResponseIncidencias(String responseBody) throws Exception {

        ObjectIncidencia obj = deserializador.getObjectIncidencias(responseBody);

        if (!obj.getStatus().equals("OK")) {

            throw new Exception(obj.getDesc());
        }

        return obj;
    }//end getResponseIncidencias

}
