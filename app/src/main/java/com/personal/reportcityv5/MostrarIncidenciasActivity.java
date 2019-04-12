package com.personal.reportcityv5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Adaptadores.IncidenciaAdapter;
import modelo.GlobalValues;
import modelo.Incidencia;

public class MostrarIncidenciasActivity extends AppCompatActivity {

    private ListView listaIncidencias ;
    private static String TAG = "MostrarIncidenciasActivity";
    private Toolbar toolbar ;

    private GlobalValues variableGlobal;



    List<Incidencia> fdsdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_incidencias);

        variableGlobal = (GlobalValues) getApplicationContext();





        incializaElementos();

        configuraListaIncidencias();

        setListenerBack();

    }

    private void incializaElementos() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_mostrar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.atras);

        listaIncidencias =  (ListView) findViewById( R.id.listaIncidencias);

    }

    public List<Incidencia> geArrayIncidencias(Set<Incidencia> inscc){

        List<Incidencia> lds = new ArrayList<Incidencia>();

        for(Incidencia ind : inscc){

            lds.add(ind);
        }

        Log.i(TAG,"Sise incidencias: " + lds.size());
        return lds;

    }

    public ArrayList<Incidencia> geArrayIncidencias2(Set<Incidencia> inscc){

        ArrayList<Incidencia> lds = new ArrayList<Incidencia>();

        for(Incidencia ind : inscc){

            lds.add(ind);
        }

        Log.i(TAG,"Sise incidencias: " + lds.size());
        return lds;

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

  public void configuraListaIncidencias(){


        //adaptador = new ListaIncidenciasAdaptador(this, geArrayIncidencias (variableGlobal.getUsuarioActivo().getIncidencias() ),getApplicationContext());

       //  listaIncidencias.setAdapter(adpater);

      IncidenciaAdapter dap = new IncidenciaAdapter(this,geArrayIncidencias2( variableGlobal.getUsuarioActivo().getIncidencias() ));

      listaIncidencias.setAdapter( dap);

  }//end configuraListaIncidencias
}
