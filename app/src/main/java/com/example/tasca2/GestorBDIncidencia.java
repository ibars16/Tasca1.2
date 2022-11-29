package com.example.tasca2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GestorBDIncidencia extends SQLiteOpenHelper {
    private static final String KEY_ID = "id_incidencia";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_DESCRIPCIO = "descripcio";
    private static final String KEY_ELEMENT = "element";
    private static final String KEY_TIPUS = "tipus";
    private static final String KEY_UBICACIO = "ubicacio";
    private static final String KEY_DATE = "date";

    public GestorBDIncidencia(Context context) {
        super(context, "incidencias", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE incidencia ("
                + "id_incidencia INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT," +
                "descripcio TEXT," +
                "element TEXT," +
                "tipus TEXT," +
                "ubicacio TEXT," +
                "date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//    public void cargarIncidencias() {
//        guardarIncidencia("primera");
//    }
//
//    private void guardarIncidencia(String nombre) {
//        SQLiteDatabase db = getWritableDatabase();
//        guardarIncidencias(db, nombre);
//    }
//
//    private void guardarIncidencias(SQLiteDatabase db, String nombre) {
//        ContentValues cv = new ContentValues();
//        cv.put("nombre", nombre);
//        db.insertOrThrow("incidencia", null, cv);
//    }

    @SuppressLint("Range")
    public List<Incidencia> getIncidencias(){
        SQLiteDatabase db = getWritableDatabase();
        HashMap<String, Incidencia>  incidenciaList = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT * FROM incidencia ORDER BY id_incidencia" ,null);

        while (cursor.moveToNext()){
            Incidencia incidencia = this.getElementsIncidencia(cursor);
            incidenciaList.put(incidencia.getId_incidencia(),incidencia);

        }

        return new ArrayList<>(incidenciaList.values());
    }

    @SuppressLint("Range")
    public Incidencia getIncidencia(int id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM incidencia WHERE id_incidencia = "+ id ,null);
        cursor.moveToFirst();
        Incidencia incidencia = this.getElementsIncidencia(cursor);

        return incidencia;
    }

    public void changeEstatIncidencia(String tipus, int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE incidencia SET tipus = '"+ tipus +"' WHERE id_incidencia = "+id);
    }

    public void createIncidencia(String nombre, String descripcion, String element, String tipus, String ubicacio, String tiempo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("descripcio", descripcion);
        cv.put("element", element);
        cv.put("tipus", tipus);
        cv.put("ubicacio", ubicacio);
        cv.put("date", tiempo);
        db.insertOrThrow("incidencia", null, cv);
    }

    @SuppressLint("Range")
    private Incidencia getElementsIncidencia(Cursor cursor){
         Incidencia incidencia = new Incidencia(
                cursor.getString(cursor.getColumnIndex(KEY_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_NOMBRE)),
                cursor.getString(cursor.getColumnIndex(KEY_DESCRIPCIO)),
                cursor.getString(cursor.getColumnIndex(KEY_ELEMENT)),
                cursor.getString(cursor.getColumnIndex(KEY_TIPUS)),
                cursor.getString(cursor.getColumnIndex(KEY_UBICACIO)),
                cursor.getString(cursor.getColumnIndex(KEY_DATE)));

        return incidencia;
    }
}
