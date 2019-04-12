package Servicio;

import modelo.ObjectCategoria;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceCategorias {

    String pathCategorias = "categorias/";

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=utf-8"
    })

    @GET( pathCategorias )
    Call<String> getCategorias();


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=utf-8"
    })

    @POST( pathCategorias+"{categoriaData}" )
    Call<String> updCategoria(@Path("categoriaData") String categoriaData);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=utf-8"
    })
    @PUT( pathCategorias+"{categoriaData}" )
    Call<String> newCategoria(@Path("categoriaData") String categoriaData);
}
