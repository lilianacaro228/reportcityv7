package Bean;

import Servicio.ServiceCategorias;
import modelo.Categorias;
import modelo.ObjectCategoria;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.Deserializador;
import utils.Serializador;

public class BeanServiceCategorias {

    //private String serviceURL = "http://reportcity.org/api-cityv2/api-rest/";
    private String serviceURL = "http://report-city.org/api-cityv3/api-rest/";

    private Retrofit retrofit;

    private Serializador serial;

    private Deserializador deserializador;

    private Categorias user = new Categorias();

    private static ObjectCategoria obj = new ObjectCategoria();

    private static final String TAG = "BeanServiceCategorias";

    private ServiceCategorias cliente;

    public BeanServiceCategorias() {
    }



    public void iniciaRetrofit(){

        retrofit = new Retrofit.Builder()
                .baseUrl( serviceURL )
                .addConverterFactory( ScalarsConverterFactory.create() )
                .build();

        cliente = retrofit.create(ServiceCategorias.class);

       // serial = new Serializador();

        deserializador = new Deserializador();


    }//end iniciaRetrofit


    public Call<String> getCliente(String modo, String datos ){

        Call<String> call = null;

        switch (modo){

            case "GET": call = cliente.getCategorias();
                break;

            case "POST": call = cliente.updCategoria( datos );
                break;

            case "PUT": call = cliente.newCategoria( datos);
                break;

            default: call = null;

        }

        return call;


    }//end getCliente


    public ObjectCategoria getResponseCategoria ( String responseBody) throws Exception {

        ObjectCategoria obj = deserializador.getObjectCategorias(responseBody);

        if (!obj.getStatus().equals("OK")) {

            throw new Exception(obj.getDesc());
        }

        return obj;
    }//getResponseCategoria



}
