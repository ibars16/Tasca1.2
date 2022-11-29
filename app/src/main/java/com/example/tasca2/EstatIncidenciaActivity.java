package com.example.tasca2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class EstatIncidenciaActivity extends AppCompatActivity {
    String[] types = {"Resuelta","No Resuelta"};
    Spinner typeSpinner;


    public GestorBDIncidencia gbdRest = new GestorBDIncidencia(this);


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Incidencia incidencia = null;
        setContentView(R.layout.estat_incidencia);
        String id = getIntent().getStringExtra("id");
        this.typeSpinner();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, String.valueOf(0))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("App Incidencias")
                .setContentText("La incidencia "+ id+" ha sido resulta")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if(id != null){
            incidencia = gbdRest.getIncidencia(Integer.parseInt(id)+1);
            if(incidencia != null){
                TextView nombreText = (TextView)findViewById(R.id.nombre);
                TextView descripcioText = (TextView)findViewById(R.id.descripcio);
                TextView dateText = (TextView)findViewById(R.id.date);
                TextView elementText = (TextView)findViewById(R.id.element);
                TextView ubicacioText = (TextView)findViewById(R.id.ubicacio);
                TextView tipusText = (TextView)findViewById(R.id.tipus);
                Button butonguardar = (Button)findViewById(R.id.guardar);
                TextView tituloText = (TextView)findViewById(R.id.textView2);

                for (int i = 0; i < this.types.length; i++) {
                    if(this.types[i].equals(incidencia.getTipus())){
                        this.typeSpinner.setSelection(i);
                    }
                }

                butonguardar.setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View view) {
                                gbdRest.changeEstatIncidencia((String) typeSpinner.getSelectedItem(), Integer.parseInt(id)+1);
                                if(typeSpinner.getSelectedItem().equals("Resuelta")){
                                    notificationManager.notify(0, builder.build());
                                }
                            }
                        });
                tituloText.setText(tituloText.getText()+ " " + incidencia.getId_incidencia());
                nombreText.setText(incidencia.getNombre());
                descripcioText.setText(incidencia.getDescripcio());
                dateText.setText(incidencia.getDate());
                elementText.setText(incidencia.getElement());
                ubicacioText.setText(incidencia.getUbicacio());
            }
        }
    }
    private void typeSpinner(){
        this.typeSpinner = (Spinner)findViewById(R.id.typeSpinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, this.types);
        this.typeSpinner.setAdapter(arrayAdapter);
    }
}
