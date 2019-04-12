package com.personal.reportcityv5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Bean.BeanServiceIncidencia;
import modelo.Categorias;
import modelo.GlobalValues;
import modelo.Incidencia;
import modelo.IncidenciaId;
import modelo.ObjectCategoria;
import modelo.ObjectIncidencia;
import modelo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Serializador;
import utils.Utilitarios;

public class IncidenciaActivity extends AppCompatActivity  {


    private static String TAG="IncidenciaActivity";

    private AutoCompleteTextView titulo;
    private AutoCompleteTextView barrio_sector;
    private AutoCompleteTextView desc;

    private Spinner spinCat;

    private GlobalValues variableGlobal;

    private  Toolbar toolbar;

    private MenuItem btn_guarda_incidencia;
    private MenuItem btn_borra_incidencia;
    private MenuItem btn_editar_incidencia;


    private Incidencia incidencia;


   private Categorias categoriaSeleccionada;
    private int id_categoria = - 1;

    private BeanServiceIncidencia beanIncidencia;
    private ObjectIncidencia objIncidencia;

    private Serializador serial;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencia);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        variableGlobal = (GlobalValues) getApplicationContext();
        incidencia = variableGlobal.getIncidenciaActiva();
        /*
         modoBtn = (String) getIntent().getExtras().get("modo");
         incidencia = (Incidencia) getIntent().getExtras().getSerializable("incidencia");
        categoriaObj = (ObjectCategoria) getIntent().getExtras().getSerializable("ObjectCategoria");
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        tools = (Utilitarios) getIntent().getExtras().getSerializable("tools");
*/

        Log.i(TAG, "EL modo es: " + variableGlobal.getModoIncidencia());



        inicializaElementos();

        setListenerBack();

        setListenerSpinnerCategoria();
        //setListenerCategoria();

        setDatosInicialesIncidencia();




    }

    private void setDatosInicialesIncidencia() {

        try{

            barrio_sector.setText( incidencia.getBarrioSector());

        }catch (Exception ex){
           mensajeConfirmacion("Ohh no","Hubo un error en obtener tu ubicacion actual");
            Log.i(TAG, ex.getLocalizedMessage());
        }
    }

    private void setListenerSpinnerCategoria() {

        spinCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

              //  categoriaSeleccionada = spinCat.getSelectedItem().toString();
                Log.i(TAG,"item Seleccionado: " +  spinCat.getSelectedItem().toString());

                id_categoria = spinCat.getSelectedItemPosition()+1;
                Log.i(TAG, "posistion: "+id_categoria);


                for( Categorias cate: variableGlobal.getCategoriasGenerales()){
                    if( cate.getNombreCategoria().equals( spinCat.getSelectedItem().toString() )){
                        categoriaSeleccionada = cate;
                        break;
                    }


                }

                Log.i(TAG,categoriaSeleccionada.getNombreCategoria());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                    presenteMensaje("Debes escoger una categoría de la incidencia");
            }
        });

    }//setListenerSpinnerCategoria



    public void presentaCategoriasDialogo(){

       /* final List<String> categos = getCategoriasArray();

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder( IncidenciaActivity.this);
        dialogo1.setTitle(  "Escojar una categoría" );
        dialogo1.setIcon(R.drawable.lista);

        dialogo1.setItems(categos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    categoria.setText(  categos.get(i) );
                    id_categoria = i;
            }
        });


        dialogo1.setCancelable(false);

        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                    if( id_categoria > 0){

                        dialogo1.dismiss();

                    }else{

                        presenteMensaje("Debes escoger una categoria");

                    }


            }
        });
        AlertDialog dialog = dialogo1.create();
        dialog.show();*/
    }

    private List<String> getCategoriasArray() {

        List<String> cats = new ArrayList<>();

        //cats.add(0,"Seleccione una Categoria");

        List<Categorias> css = variableGlobal.getCategoriasGenerales();

        for(Categorias cat : css ){

            //cats.add(cat.getIdCategorias(),cat.getNombreCategoria());
            cats.add(cat.getNombreCategoria());

        }

        return cats;

    }


    public void setListenerBack(){

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }
        });

    }


    public void setBotonesCabecera(){

            if( variableGlobal.getModoIncidencia().equals("NEW")){

                toolbar.setTitle("Crear Incidencia");
                btn_guarda_incidencia.setVisible(true);
                btn_borra_incidencia.setVisible(false);
                btn_editar_incidencia.setVisible(false);
            }

            if( variableGlobal.getModoIncidencia().equals("DELETE")){

                toolbar.setTitle("Borrar Incidencia");
                btn_borra_incidencia.setVisible(true);

                btn_guarda_incidencia.setVisible(false);
                btn_editar_incidencia.setVisible(false);

            }

        if( variableGlobal.getModoIncidencia().equals("UPD")){

            toolbar.setTitle("Actualizar Incidencia");
            btn_borra_incidencia.setVisible(false);

            btn_guarda_incidencia.setVisible(false);
            btn_editar_incidencia.setVisible(true);

        }


    }

    public void inicializaElementos(){

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.atras);
    /*
        btn_guarda_incidencia = (MenuItem) findViewById(R.id.btn_guarda_incidencia) ;
        btn_borra_incidencia = (MenuItem) findViewById(R.id.btn_borrar_incidencia);
        btn_editar_incidencia  = (MenuItem) findViewById(R.id.btn_editar_incidencia);
*/

        titulo = (AutoCompleteTextView) findViewById(R.id.titulo);
        barrio_sector = (AutoCompleteTextView) findViewById(R.id.barrio_sector);

        desc = (AutoCompleteTextView) findViewById(R.id.desc);


        spinCat = (Spinner)  findViewById(R.id.spiner_categorias);

        List<String> catsss = getCategoriasArray();

        ArrayAdapter<String> adp = new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1,catsss);

        adp.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spinCat.setAdapter(adp);


    }//end inicializaElementos


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_incidencia, menu);

        btn_guarda_incidencia = (MenuItem) menu.findItem(R.id.btn_guarda_incidencia) ;
        btn_borra_incidencia = (MenuItem) menu.findItem(R.id.btn_borrar_incidencia);
        btn_editar_incidencia  = (MenuItem) menu.findItem(R.id.btn_editar_incidencia);

        setBotonesCabecera();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btn_guarda_incidencia) {
            Log.i(TAG,"Presiona guarda incidencia");

            if( hayDatosNulos(  ) ){

                presenteMensaje("Por favor  completa todos los campos");
            }else{

                incidencia.setBarrioSector( barrio_sector.getText().toString());
                incidencia.setTitulo( titulo.getText().toString());
                incidencia.setDescripcionAdicional( desc.getText().toString());
                incidencia.setFechaModificacion(new Date());
                incidencia.setFechaCreacion( new Date());
                //incidencia.setCategorias( categoriaSeleccionada);
                incidencia.setId( new IncidenciaId(null,variableGlobal.getUsuarioActivo().getIdUser(),categoriaSeleccionada.getIdCategorias() ));

                guardaIncidencia( incidencia);

            }


            return true;
        }else{

            if( id == R.id.btn_borrar_incidencia){

                Log.i(TAG,"Presiona Cerrar incidencia");
                return true;
            }

        }

        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected



    public void guardaIncidencia(Incidencia incidencia){

            beanIncidencia = new BeanServiceIncidencia();

            beanIncidencia.iniciaRetrofit();

            serial = new Serializador();
        JSONObject serializadoIncidencia = null;

        try {

             serializadoIncidencia = serial.serialIncidencia(incidencia);

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,e.getLocalizedMessage());
            presenteMensaje( e.getLocalizedMessage() );
        }

        Log.i(TAG, "Envio Incidencia: " + serializadoIncidencia.toString());

        Call<String> call = beanIncidencia.getCliente("PUT",serializadoIncidencia.toString() );

        call.enqueue(new Callback<String>() {


            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.i(TAG, "Respuesta Servicio Incidencia: " + response);

                if (response.code() == 200) {
                    Log.i(TAG, "Respuesta Body: " + response.body());

                    try {

                        objIncidencia = beanIncidencia.getResponseIncidencias( response.body());

                        if(objIncidencia.getStatus().equals("OK")){
                            //Agrego la incidencia recientemente creada a la variable global
                            variableGlobal.getUsuarioActivo().getIncidencias().add(objIncidencia.getIncidencia());
                            variableGlobal.setIncidenciaActiva(null);
                            variableGlobal.setModoIncidencia("N/D");
                            mensajeConfirmacion( "Registro Existoso!!!","Tu incidencia ha sido guardada con exito: " );

                        }else{


                            presenteMensaje( objIncidencia.getDesc() );

                        }

                    } catch (Exception e) {
                        presenteMensaje(e.getLocalizedMessage());
                        Log.i(TAG, e.getLocalizedMessage());
                        //e.printStackTrace();
                    }

                }else{

                    presenteMensaje("Hubo un error al registrar tu Incidencia!!");
                }

            }//end onResponse

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }//end onFailure


        });//ENd Call

    }//end guardaIncidencia


    public void mensajeConfirmacion(String titulo, String mensaje){

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder( IncidenciaActivity.this);
        dialogo1.setTitle(  titulo );

        dialogo1.setMessage( mensaje);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("incidenciaNew",objIncidencia.getIncidencia());
                startActivity(intent);

            }
        });
        AlertDialog dialog = dialogo1.create();
        dialog.show();
    }

    public void getDatosIndicencia(){

            incidencia.setEliminado("NO");
            incidencia.setEstado("ACTIVO");
            incidencia.setFechaCreacion(new Date());
            incidencia.setFechaModificacion(new Date());

            if( hayDatosNulos(  )){

                presenteMensaje("Completa todos los campos");

            }else{


                incidencia.setTitulo( titulo.getText().toString());
                incidencia.setBarrioSector( barrio_sector.getText().toString());
                incidencia.setDescripcionAdicional( desc.getText().toString());


            }





    }//getDatosIndicencia


    public void presenteMensaje(String mensaje){

        Snackbar.make(getCurrentFocus(), mensaje, Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null).show();

    }

    public boolean hayDatosNulos(  ){

        return TextUtils.isEmpty( titulo.getText().toString() ) ||
                TextUtils.isEmpty( barrio_sector.getText().toString() ) || TextUtils.isEmpty( desc.getText().toString() ) ||  id_categoria < 0 ||
                TextUtils.isEmpty( incidencia.getLatitud() ) || TextUtils.isEmpty(incidencia.getLongitud())
                ;

    }//hayDatosNulos


}
