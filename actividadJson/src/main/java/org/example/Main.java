package org.example;

import controlador.CursoService;
import model.Alumno;
import model.Curso;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        CursoService cursoService = new CursoService();
        Curso curso = cursoService.cargarCurso();
        //total alumnos
        System.out.println("Total alumnos : " + curso.getAlumnos().size());

        System.out.println("Curso: " + curso.getCurso());

        for (Alumno alumno : curso.getAlumnos()) {
            System.out.println("Nombre: " + alumno.getNombre() + "edad: " + alumno.getEdad() + "años");
        }

        //alumnos repetidores
        for (Alumno alumno : curso.getAlumnos()) {
            if (alumno.isRepetidor()) {
                System.out.println("Nombre: " + alumno.getNombre());
            }
        }

        System.out.println("Escribe el modulo: ");
        String modulo = new Scanner(System.in).nextLine();

        for (Alumno alumno : curso.getAlumnos()) {
            if (alumno.getModulos().contains(modulo)) {
                System.out.println("Nombre: " + alumno.getNombre() + "edad: " + alumno.getEdad());
            }
        }

        //cargar un nuevo alumno
        Alumno alumnoNuevo = new Alumno(3, "Hiba", 20, List.of("DI", "PSP"), false);
        curso.agregarAlumno(alumnoNuevo);


        System.out.println("listar");
        for (Alumno alumno : curso.getAlumnos()) {
            System.out.println("Nombre: " + alumno.getNombre() + " edad: " + alumno.getEdad());
        }
        cursoService.guardarCurso(curso);


    }
}