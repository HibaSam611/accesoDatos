package org.example.model;

public class Estudio {
    private String titulo;
    private String centro;
    private int anio;

    public Estudio(String titulo, String centro, int anio) {
        this.titulo = titulo;
        this.centro = centro;
        this.anio = anio;
    }
    public Estudio(){}
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getCentro() {
        return centro;
    }
    public void setCentro(String centro) {
        this.centro = centro;
    }
    public int getAnio() {
        return anio;
    }
    public void setAnio(int anio) {
        this.anio = anio;
    }


}
