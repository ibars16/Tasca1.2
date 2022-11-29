package com.example.tasca2;

public class Incidencia {

    private String id_incidencia;
    private String nombre;
    private String descripcio;
    private String element;
    private String tipus;
    private String ubicacio;
    private String date;

    public Incidencia(String id_incidencia, String nombre, String descripcio, String element, String tipus, String ubicacio, String date) {
        this.id_incidencia = id_incidencia;
        this.nombre = nombre;
        this.descripcio = descripcio;
        this.element = element;
        this.tipus = tipus;
        this.ubicacio = ubicacio;
        this.date = date;
    }

    public String getId_incidencia() {
        return id_incidencia;
    }

    public void setId_incidencia(String id_incidencia) {
        this.id_incidencia = id_incidencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "id_incidencia='" + id_incidencia + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
