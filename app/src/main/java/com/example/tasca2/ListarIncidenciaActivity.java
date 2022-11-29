package com.example.tasca2;

        import android.content.Intent;
        import android.os.Bundle;

        import androidx.appcompat.app.AppCompatActivity;

        import android.view.View;


        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.Spinner;

        import java.util.List;

public class ListarIncidenciaActivity extends AppCompatActivity {
    String[] types = {"Resuelta","No Resuelta"};
    Spinner typeSpinner;

    public GestorBDIncidencia gbdRest = new GestorBDIncidencia(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_view);

        List<Incidencia> incidencias = gbdRest.getIncidencias();
        ListView incidenciasMostrar =  (ListView)findViewById(R.id.list);

        IncidenciasAdapter incidenciasAdapter = new IncidenciasAdapter(ListarIncidenciaActivity.this, incidencias);
        incidenciasMostrar.setAdapter(incidenciasAdapter);


        incidenciasMostrar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                lanzarModificarEstadoIncidencia(null, position);
            }
        });

    }


    public void lanzarModificarEstadoIncidencia(View view, int id) {
        Intent i = new Intent(this, EstatIncidenciaActivity.class);
        i.putExtra("id",String.valueOf(id));
        startActivity(i);
    }



}
