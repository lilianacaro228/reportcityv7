package com.personal.reportcityv5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import Bean.BeanServiceProvider;
import modelo.ObjectUser;
import modelo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Serializador;
import utils.Utilitarios;

public class RegistroActivity extends AppCompatActivity {

    private AutoCompleteTextView nombres;
    private AutoCompleteTextView apellidos;
    private AutoCompleteTextView email_registro;
    private EditText password_registro1;
    private EditText password_registro2;

    private static String TAG = "RegistroActivity";

    private BeanServiceProvider beanUser;


    private  ConnectivityManager connectivityManager ;

    private FloatingActionButton registro_btn;
    private Utilitarios tools;

    private ObjectUser responseRegistro = new ObjectUser();

    private Serializador serial = new Serializador();

    AlertDialog.Builder dialogo1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        connectivityManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);

        tools = (Utilitarios) getIntent().getExtras().getSerializable("tools");

        /*  registro_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        incializaElementos();

        registrarUsuarioListener();

    }//ned onCreate

    public void incializaElementos(){
          nombres = (AutoCompleteTextView) findViewById(R.id.nombres);
          apellidos = (AutoCompleteTextView) findViewById(R.id.apellidos);
          email_registro =(AutoCompleteTextView) findViewById(R.id.email_registro);
          password_registro1 = (EditText) findViewById(R.id.password_registro);
          password_registro2 = (EditText) findViewById(R.id.password_registro2);
        registro_btn = (FloatingActionButton) findViewById(R.id.fab);

    }//incializaElementos


    public void registrarUsuarioListener(){

       /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    */

        registro_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Usuario newUser = new Usuario();


                if( tools.conexionInternet( connectivityManager ) ) {


                    if( hayDatosNulos() ){

                        presenteMensaje("Para registrarte necesitas completar todos los campos");

                    }else{

                        newUser.setNombres( nombres.getText().toString() );
                        newUser.setApellidos( apellidos.getText().toString() );
                        newUser.setMail( email_registro.getText().toString() );
                        newUser.setFechaCreacion( new Date());
                        newUser.setFechaModificacion( new Date());



                        if( password_registro1.getText().toString().equals( password_registro2.getText().toString()  ) ){

                            //newUser.setClave( ( password_registro1.getText().toString() ) );
                            newUser.setClave( tools.getMd5( password_registro1.getText().toString() ) );

                            try {

                                RegistraUsuario( newUser );

                            } catch (JSONException e) {
                                Log.i(TAG, e.getLocalizedMessage());
                                presenteMensaje(  e.getLocalizedMessage() );
                            } catch (Exception e) {
                                Log.i(TAG, e.getLocalizedMessage());
                                e.printStackTrace();

                                presenteMensaje(  e.getLocalizedMessage() );
                            }

                        }else{
                            presenteMensaje("Las contraseñas ingresadas no coinciden!!!");
                        }


                    }




                }else{

                    presenteMensaje("¡¡Necesitas conexión a internet para utilizar reporcity app!");
                }


            }
        });


    }//end registrarUsuarioListener

    public void RegistraUsuario(final Usuario userNew) throws JSONException,Exception {

        beanUser = new BeanServiceProvider();

        beanUser.iniciaRetrofit();

        JSONObject usernewData = serial.serialUsuario(userNew);

        usernewData.remove("idUser");
        usernewData.remove("incidencias");
        usernewData.remove("usuarioActivo");



        Call<String> call = beanUser.getCliente("PUT", usernewData.toString() );

        Log.i(TAG, "Envio Registro: " + usernewData.toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.i(TAG, "Respuesta Servicio: " + response);
                if (response.code() == 200) {
                    Log.i(TAG, "Respuesta Body: " + response.body());
                    String dato = "";
                    try{

                        responseRegistro = beanUser.getResponseUser( response.body() );

                        if(responseRegistro.getStatus().equals("OK")){


                            mensajeConfirmacion( "Registro Existoso!!!","Para completar tu registro, sigue las instrucciones enviadas a tu correo electrónico: "+ userNew.getMail()+". No olvides revisar en correos no desados." );

                        }else{


                            presenteMensaje( responseRegistro.getDesc() );

                        }




                    }catch (Exception ex ){

                        presenteMensaje( ex.getLocalizedMessage() );
                    }





                }else{

                    presenteMensaje("Hubo un error al registrar tu usuario!!");
                }

            }// onResponse

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                presenteMensaje( t.getLocalizedMessage() );
            }
        });//ENd Call


    }//end RegistraUsuario

    public void mensajeConfirmacion(String titulo, String mensaje){

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder( RegistroActivity.this);
        dialogo1.setTitle(  titulo );

        dialogo1.setMessage( mensaje);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(intent);

            }
        });
        AlertDialog dialog = dialogo1.create();
        dialog.show();
    }


    public boolean hayDatosNulos(  ){

        return TextUtils.isEmpty( nombres.getText().toString() ) ||
               TextUtils.isEmpty( apellidos.getText().toString() ) || TextUtils.isEmpty( email_registro.getText().toString() ) ||
                TextUtils.isEmpty( password_registro1.getText().toString() ) || TextUtils.isEmpty( password_registro2.getText().toString() )

                ;

    }//hayDatosNulos

    public void presenteMensaje(String mensaje){

        Snackbar.make(getCurrentFocus(), mensaje, Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btn_cerrar_registro) {
            Log.i(TAG,"Presiona Cerrar btn");

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
