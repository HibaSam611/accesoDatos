package model;

import java.util.List;

public class Alumno {
    private int id;
    private String nombre;
    private  int edad;
    private List<String> modulos;
    private boolean repetidor;

    public Alumno(int id, String nombre, int edad, List<String> modulos, boolean repetidor) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.modulos = modulos;
        this.repetidor = repetidor;
    }

    public Alumno() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public List<String> getModulos() {
        return modulos;
    }

    public void setModulos(List<String> modulos) {
        this.modulos = modulos;
    }

    public boolean isRepetidor() {
        return repetidor;
    }

    public void setRepetidor(boolean repetidor) {
        this.repetidor = repetidor;
    }
}
