package com.personal.reportcityv5;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Bean.BeanServiceProvider;
import modelo.GlobalValues;
import modelo.ObjectUser;
import modelo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import utils.Utilitarios;

public class LoginActivity extends AppCompatActivity {



    private Button ingresoButton;

    private AutoCompleteTextView email;

    private EditText clave;

    private Button registroButton;

    private Usuario usuarioLogin,usuario;

    private static String TAG = "LoginActivity";

    private BeanServiceProvider beanUser;


    private Utilitarios tools;

    private  ConnectivityManager connectivityManager ;





    private Retrofit retrofit;



    private Usuario user = new Usuario();

    private ObjectUser responseLogin = new ObjectUser();

/*

 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        connectivityManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);




        tools = new Utilitarios();

        incializaElementos();

        loginListener();

        registroListener();

        if( !tools.conexionInternet( connectivityManager )){

            mensajeToast("¡¡Necesitas conexióna internet para utilizar reporcity app!");
        }

    }

    /**
     * Esta función sirve para agregar un lista
     * @paramn
     * */
    private void registroListener( ) {

        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( tools.conexionInternet( connectivityManager ) ) {

                    Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
                    intent.putExtra("tools", tools);
                    startActivity(intent);


                }else{

                    mensajeToast("¡¡Necesitas conexióna internet para utilizar reporcity app!");
                }

            }//ned onClick
        });//end setOnClickListener


    }//end registroListene


    public void incializaElementos(){

        ingresoButton = (Button) findViewById(R.id.auth_button);

        email = (AutoCompleteTextView) findViewById(R.id.email);

        clave = (EditText) findViewById(R.id.password) ;

        registroButton = (Button) findViewById( R.id.crear_boton);

    }//end incializaElementos


/*
*
* */
    public void loginListener(){

        ingresoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( tools.conexionInternet( connectivityManager ) ) {

                        usuarioLogin = new Usuario();



                        usuarioLogin.setMail(email.getText().toString());
                        //usuarioLogin.setMail( "santy.srz@gmail.com" );
                        usuarioLogin.setClave( tools.getMd5( clave.getText().toString() ) );
                        //usuarioLogin.setClave( tools.getMd5( "1234567" ) );


                        beanUser = new BeanServiceProvider();

                        beanUser.iniciaRetrofit();

                        try {

                            if (esNuloDatos()) {

                                mensajeToast("Usuario y clave son requeridos");
                            } else {

                                doLogin(usuarioLogin);
                            }


                        } catch (JSONException e) {
                            mensajeToast(e.getLocalizedMessage());
                        }
                }else{

                    mensajeToast("¡¡Necesitas conexióna internet para utilizar reporcity app!");
                }

            }//ned onClick
        });//end setOnClickListener


    }//end setListeners

    public boolean esNuloDatos(){

        return TextUtils.isEmpty( email.getText().toString() ) || TextUtils.isEmpty( email.getText().toString() );

    }

    public void doLogin(Usuario anonimoUser) throws JSONException {


        JSONObject loginDat = new JSONObject();


        loginDat.put("mail", anonimoUser.getMail());
        loginDat.put("clave", anonimoUser.getClave());

        Call<String> call = beanUser.getCliente("GET", loginDat.toString() );

        Log.i(TAG, "Envio: " + loginDat.toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                //Log.i(TAG, "Respuesta Servicio: " + response);
                if (response.code() == 200) {

                    String dato = "N/D";

                      Log.i(TAG, "Respuesta Body: " + response.body());
                    try {

                        responseLogin = beanUser.getResponseUser( response.body() );

                        Log.i(TAG, "obj Status : " + responseLogin.getStatus());

                        if(responseLogin.getStatus().equals("OK")){

                           //Redirijo a google maps

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);


                            Log.i(TAG,"Valor idUsuario: ("+responseLogin.getUsuario().getIdUser() +")");
                            //intent.putExtra("Usuario", responseLogin.getUsuario());
                           // intent.putExtra("Tools",tools);

                            //Seteo variable global

                            GlobalValues variableGlobal = (GlobalValues) getApplicationContext();

                            variableGlobal.setUsuarioActivo( responseLogin.getUsuario());
                            variableGlobal.setTools( tools);

                            Log.i(TAG,"# INCI LOGIN: " + variableGlobal.getUsuarioActivo().getIncidencias().size());

                            startActivity(intent);

                        }else{
                            Log.i(TAG,"Entras al error");
                            dato = responseLogin.getDesc();
                        }

                        mensajeToast( dato );


                    } catch (Exception e) {
                        Log.i(TAG, "Entras al error: " + e.getLocalizedMessage());

                        mensajeToast( e.getLocalizedMessage() );

                    }


                } else {
                    Log.i(TAG, "Entras al error: " +  response.message());
                    mensajeToast ("Hubo un error al consultar tu usuario: " + response.message());


                }
            }//onResponse

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i(TAG, "Entras on Falilure");
                mensajeToast ( t.getLocalizedMessage() );
            }//onFailure


        });


    }//end dlogin


    public void mensajeToast( String info){

        Toast toast = Toast.makeText(getApplicationContext() ,  info , Toast.LENGTH_LONG);
        toast.show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
