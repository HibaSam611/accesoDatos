package model;

import java.util.List;

public class Curso {
    private String curso;
    private List<Alumno> alumnos;

    public Curso(String curso, List<Alumno> alumnos) {
        this.curso = curso;
        this.alumnos = alumnos;
    }

    public Curso() {
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public void agregarAlumno(Alumno alumno) {
        alumnos.add(alumno);
    }


}
