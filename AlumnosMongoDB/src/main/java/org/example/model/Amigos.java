package org.example.model;

import java.util.List;

public class Amigos {
    private String nombre;
    private Integer edad;
    private List<String> hobbies;
    private List<String> telefonos;
    private List<Estudio> estudios;

    public Amigos(String nombre, Integer edad, List<String> hobbies, List<String> telefonos, List<Estudio> estudios) {
        this.nombre = nombre;
        this.edad = edad;
        this.hobbies = hobbies;
        this.telefonos = telefonos;
        this.estudios = estudios;
    }
    public Amigos(){}
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getEdad() {
        return edad;
    }
    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    public List<String> getHobbies() {
        return hobbies;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }
    public List<Estudio> getEstudios() {
        return estudios;
    }


}
