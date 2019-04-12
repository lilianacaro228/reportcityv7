package Servicio;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceIncidencia {

    String pathIncidencia = "incidencia/{incidenciaData}";

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=utf-8"
    })

    @GET( pathIncidencia )
    Call<String> getIncidencias(@Path("incidenciaData") String incidenciaData);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=utf-8"
    })

    @POST( pathIncidencia )
    Call<String> updIncidencia(@Path("incidenciaData") String incidenciaData);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=utf-8"
    })
    @PUT( pathIncidencia )
    Call<String> newIncidencia(@Path("incidenciaData") String incidenciaData);
}
