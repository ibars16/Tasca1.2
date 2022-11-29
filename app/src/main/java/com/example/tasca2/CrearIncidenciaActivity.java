package com.example.tasca2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.sql.Connection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class CrearIncidenciaActivity extends AppCompatActivity  {

    Button saveIncidence;
    Button dateButton;
    Button dayButton;
    EditText nameEditText;
    Spinner elementSpinner;
    Spinner typeSpinner;
    Spinner ubicationSpinner;
    EditText descriptionEditText;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mtimeSetListener;
    String[] elements = {"Ratoli","Monitor", "Teclat"};
    String[] types = {"No Resuelta"};
    String[] ubications = {"Clase 1DAM","Clase 2DAM", "Sala reunions"};
    public GestorBDIncidencia gbdRest = new GestorBDIncidencia(this);
    private static final String TAG = "MenuActivity";
    private String fecha;
    private String hora;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_incidencia);

        this.elementSpinner();
        this.typeSpinner();
        this.ubicationSpinner();


        saveIncidence = (Button) findViewById(R.id.button2);
        dateButton = (Button) findViewById(R.id.datee);
        dayButton = (Button) findViewById(R.id.dateday);
        nameEditText = (EditText) findViewById(R.id.nameInput);
        descriptionEditText = (EditText) findViewById(R.id.descriptionInput);
        List<Incidencia> incidencias = gbdRest.getIncidencias();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, String.valueOf(0))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("App Incidencias")
                .setContentText("La incidencia ha sido creada con exito")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        Connection con = null;

        this.saveIncidence.setOnClickListener(
                new View.OnClickListener()
                {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onClick(View view) {
                        String name = String.valueOf(nameEditText.getText());
                        String description = String.valueOf(descriptionEditText.getText());
                        if(!name.equals("") || !description.equals("")){
                            String elementName = elementSpinner.getSelectedItem().toString();
                            String typeName = typeSpinner.getSelectedItem().toString();
                            String ubicationName = ubicationSpinner.getSelectedItem().toString();
                            String localTimeString = getFecha()+" "+getHora();
                            if (localTimeString.equals("null null")){
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                LocalDateTime now = LocalDateTime.now();
                                localTimeString = dtf.format(now);
                            }
                            gbdRest.createIncidencia(name, description, elementName, typeName, ubicationName, localTimeString);
                            notificationManager.notify(0, builder.build());
                        }else{
                            Toast.makeText(CrearIncidenciaActivity.this, "El nombre y la descripción no pueden estar en blanco", Toast.LENGTH_LONG).show();
                        }


                    }
                });
        this.dateButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(
                                CrearIncidenciaActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener,
                                year,month,day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                setFecha(year + "-" + month + "-" + day);
            }
        };

        this.dayButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int hour = cal.get(Calendar.HOUR);
                        int minute = cal.get(Calendar.MINUTE);

                        TimePickerDialog dialog = new TimePickerDialog(
                                CrearIncidenciaActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mtimeSetListener,
                                hour,minute, false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });

        mtimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hourString = String.valueOf(hourOfDay);
                String minuteString = String.valueOf(minute);
                if(hourString.length() == 1){
                    hourString = "0"+hourOfDay;
                }
                if(minuteString.length() == 1){
                    minuteString = "0"+minute;
                }

                setHora(hourString+":"+minuteString+":00");
            }
        }; {

        };
    }

    private void elementSpinner(){
        this.elementSpinner = (Spinner)findViewById(R.id.elementSpinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, this.elements);
        this.elementSpinner.setAdapter(arrayAdapter);
    }
    private void typeSpinner(){
        this.typeSpinner = (Spinner)findViewById(R.id.typeSpinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, this.types);
        this.typeSpinner.setAdapter(arrayAdapter);
    }
    private void ubicationSpinner(){
        this.ubicationSpinner = (Spinner)findViewById(R.id.ubicationSpinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, this.ubications);
        this.ubicationSpinner.setAdapter(arrayAdapter);
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}

