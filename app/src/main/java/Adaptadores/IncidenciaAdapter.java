package Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.personal.reportcityv5.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import modelo.Incidencia;

public class IncidenciaAdapter extends ArrayAdapter<Incidencia> {

    private Activity activity;
    private ArrayList<Incidencia> incidencias;

    int pos;
    public static String TAG= "IncidenciaAdapter";


    public IncidenciaAdapter(Activity activity,  ArrayList<Incidencia> incidencias) {
        super(activity, R.layout.incidencia_listitem_view,incidencias);

        this.activity = activity;
        this.incidencias = incidencias;
    }



    @NonNull
    @Override
    public View getView(int pos,  @Nullable View convertView,  @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if( convertView == null ){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.incidencia_listitem_view,parent,false);

        }



        Incidencia incidenciaActual = incidencias.get(pos);

        TextView titulo = (TextView) convertView.findViewById(R.id.inci_titulo);
        TextView descripcion = (TextView) convertView.findViewById(R.id.inc_desc);
        TextView fecha = (TextView) convertView.findViewById(R.id.inc_fecha);
        TextView estado = (TextView) convertView.findViewById(R.id.inc_estado);

        Incidencia incidActual = incidencias.get( pos );

        titulo.setText( incidActual.getTitulo());
        descripcion.setText( incidActual.getDescripcionAdicional());
        fecha.setText( getFortmaFecha ( incidActual.getFechaCreacion() ) );
        estado.setText( incidActual.getEstado());

        /*
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Diste click en esta incidencia: " + incidencias.get(pos).getTitulo()  );
            }
        });
*/

        Log.i(TAG, "Vienes por aca");


        return convertView;
    }


    public String getFortmaFecha( Date fecha){

        String formateado = "N/D";

        try{

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");

            formateado = dateFormat.format(fecha);

        }catch (Exception ex ){

            Log.e(TAG, ex.getLocalizedMessage());
        }


        return formateado;

    }//end getFortmaFecha
}
