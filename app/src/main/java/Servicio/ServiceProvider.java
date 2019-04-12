package Servicio;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceProvider {

    String pathUsuario = "usuario/{userData}";
    /*
     Usuario
     */

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=utf-8"
    })

    @GET( pathUsuario )
    Call<String>  loginUsuario(@Path("userData") String userData);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=utf-8"
    })

    @POST( pathUsuario )
    Call<String> updUsuario(@Path("userData") String userData);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=utf-8"
    })
    @PUT( pathUsuario )
    Call<String> newUsuario(@Path("userData") String userData);

}
