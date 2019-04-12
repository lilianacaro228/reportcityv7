package com.personal.reportcityv5;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Bean.BeanServiceCategorias;
import modelo.Categorias;
import modelo.GlobalValues;
import modelo.Incidencia;
import modelo.ObjectCategoria;
import modelo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import utils.Utilitarios;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private FloatingActionButton btnRegistroIncidencia ;
    private Toolbar toolbar;
    private  DrawerLayout drawer ;
    private NavigationView navigationView;
    private TextView cuenta_usuario;
    private TextView correo_usuario;

    private GoogleMap mMap;

    private LocationManager gpsDevice;

    private double longitude;

    private double latitude;

    private Location location;

    private boolean mLocationPermissionGranted;

    private static String TAG = "MainActivity";

    private Object mLastKnownLocation;

    private Incidencia incidencia;



    private String direccion;
    private String ciudad;


    private ConnectivityManager connectivityManager ;



    private GlobalValues variableGlobal;

    //Categorias Varialbels

    private List<Categorias> categorias;


    private Retrofit retrofit;

    private ObjectCategoria responseCategorias = new ObjectCategoria();

    private BeanServiceCategorias beanCat ;


    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "ON creante");

        //tools = (Utilitarios) getIntent().getExtras().getSerializable("Tools");
        //usuario = (Usuario) getIntent().getExtras().getSerializable("Usuario") ;

         variableGlobal = (GlobalValues) getApplicationContext();

         Log.i(TAG,"# INCI: " + variableGlobal.getUsuarioActivo().getIncidencias().size());

        inicializaElementos();

        connectivityManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);



        if( ActivityCompat.checkSelfPermission(  this,Manifest.permission.ACCESS_FINE_LOCATION) != getPackageManager().PERMISSION_GRANTED  &&

                ActivityCompat.checkSelfPermission( this,Manifest.permission.ACCESS_COARSE_LOCATION) != getPackageManager().PERMISSION_GRANTED

                ){

                activaGpsDevice();


       }






            inicializaMapa();

            btn_registroIncidencia();


        getCategoriasIncidencias();

        setDatosInfoCuenta();



    }//End onCreate

    private void activaGpsDevice() {

        android.support.v4.app.ActivityCompat.requestPermissions(this,new String[]{

                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION
        },PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

    }



    private void setDatosInfoCuenta() {
        Log.i(TAG,"Set Datos: "+variableGlobal.getUsuarioActivo().getNombres());
        cuenta_usuario.setText( variableGlobal.getUsuarioActivo().getNombres() + " " + variableGlobal.getUsuarioActivo().getApellidos());
        correo_usuario.setText( variableGlobal.getUsuarioActivo().getMail());
    }

    private void getCategoriasIncidencias() {

        categorias = new ArrayList<Categorias>();

        beanCat = new BeanServiceCategorias();

        beanCat.iniciaRetrofit();

        if ( variableGlobal.getTools().conexionInternet(connectivityManager)) {

            consultaCategoriasWS();
        } else {
            mensajeToast("¡¡Necesitas conexióna internet para utilizar reporcity app!");

        }





    }//getCategoriasIncidencias



    public void consultaCategoriasWS(){

        Call<String> call = beanCat.getCliente("GET","");



        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.i(TAG, "Respuesta Body: " + response.body());

                if (response.code() == 200) {

                    try{

                        responseCategorias = beanCat.getResponseCategoria( response.body() );

                        Log.i(TAG, "Categorias Status : " + responseCategorias.getStatus());

                        if(!responseCategorias.getStatus().equals("OK")){


                           // mensajeToast ("Hubo un error al consultar las categorias de incidencias ");

                            mensajeConfirmacion("Ohh no!!!","No pudiemos obtener la lista de categorias en este momento intentalo mas tarde ");

                        }else{

                            variableGlobal.setCategoriasGenerales( responseCategorias.getCategorias());
                        }
                    }catch (Exception ex ){

                        Log.i(TAG,"Errors Try enqueue: "+ ex.getLocalizedMessage());
                      //  mensajeToast ( ex.getLocalizedMessage());}
                        mensajeConfirmacion("Ohh no!!!","No pudiemos obtener la lista de categorias en este momento intentalo mas tarde ");
                    }

                }else{

                    Log.i (TAG,"Hubo un error al consultar las categorias de incidencias ");

                    mensajeConfirmacion("Ohh no!!!","No pudiemos obtener la lista de categorias en este momento intentalo mas tarde ");

                }

            }//end onResponse

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.i(TAG, "Failure enqueque");
                //mensajeToast ( t.getLocalizedMessage() );
                mensajeConfirmacion("Ohh no!!!","No pudiemos obtener la lista de categorias en este momento intentalo mas tarde ");
            }

        });



    }//end consultaCategoriasWS

    public void mensajeConfirmacion(String titulo, String mensaje){

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder( MainActivity.this);
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

    public void mensajeToast( String info){

        Toast toast = Toast.makeText(getApplicationContext() ,  info , Toast.LENGTH_LONG);
        toast.show();


    }//end mensajeToast

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    public void DialogoMensaje(String titulo, String mensaje){

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder( MainActivity.this);
        dialogo1.setTitle(  titulo );

        dialogo1.setMessage( mensaje);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                /*Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(intent);*/

            }
        });
        AlertDialog dialog = dialogo1.create();
        dialog.show();
    }

public void inicializaMapa(){

    gpsDevice = (LocationManager) getSystemService(this.LOCATION_SERVICE);

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);




        return;
    }

    location = gpsDevice.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);


}

    public void inicializaElementos(){

        btnRegistroIncidencia = (FloatingActionButton) findViewById(R.id.btn_registro_incidencia);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cuenta_usuario = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nombre_cuenta_usuario);
        correo_usuario = (TextView) navigationView.getHeaderView(0).findViewById(R.id.correo_cuenta_usuario);

    }


    public void btn_registroIncidencia(){

        btnRegistroIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/




                Intent intent = new Intent(getApplicationContext(), IncidenciaActivity.class);

                incidencia = new Incidencia();

                incidencia.setLatitud( location.getLatitude() + "");
                incidencia.setLongitud( location.getLongitude() + "");
                incidencia.setBarrioSector( ciudad + ": " + direccion );
                incidencia.setEstado("ACTIVO");
                incidencia.setEliminado("NO");
                //incidencia.setUsuario(usuario);

                /*
                intent.putExtra( "modo", "NEW" );
                intent.putExtra("incidencia",incidencia);
                intent.putExtra("usuario",usuario);
                intent.putExtra("tools",tools);
                intent.putExtra("ObjectCategoria",responseCategorias);
                */

                variableGlobal.setModoIncidencia("NEW");
                variableGlobal.setIncidenciaActiva( incidencia);

                startActivity(intent);



            }
        });


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.btn_listIncidencias) {

           Log.i(TAG, "Clikc log incidencias");
            Intent intent = new Intent(getApplicationContext(), MostrarIncidenciasActivity.class);
            startActivity(intent);

        } else if (id == R.id.btn_actualizaciones) {
            Log.i(TAG, "Actualizaciones");
        } else if (id == R.id.btn_contacto) {

        } else if (id == R.id.btn_mi_cuenta) {

        } else if (id == R.id.btn_salir_app) {



            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




        if( location != null) {
            getLocalizacionActual();
        }else{

            Log.i("TAG", "Location null");
        }



    }

    public void getLocalizacionActual(){


        if( location != null ){
            // Add a marker in Sydney and move the camera
            LatLng enAlgunLugar = new LatLng(location.getLatitude(), location.getLongitude());

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> direcciones = null;
            try {
                direcciones = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address dir = direcciones.get(0);

            //direccion = dir.getAddressLine(0) + " Ciudad " + dir.getLocality();
            direccion = dir.getAddressLine(0) ;

           // Log.i(TAG,direccion);

             ciudad = dir.getLocality();

            direccion = dir.getAddressLine(0) + " " + ciudad ;

            mMap.addMarker(new MarkerOptions().position(enAlgunLugar).title(  "Posición actual" ));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(enAlgunLugar));

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target( enAlgunLugar )
                    .zoom(18)
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }else{

            DialogoMensaje("GPS Desactiado","Por favor activa tu GPS");
        }

    }
}
