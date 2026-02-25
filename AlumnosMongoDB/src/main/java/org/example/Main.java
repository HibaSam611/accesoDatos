package org.example;

import org.example.DAO.AmigoDAO;
import org.example.model.Amigos;
import org.example.model.Estudio;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        AmigoDAO amigoDAO = new AmigoDAO();

        int opcion;
        do {

            System.out.println("""
                     Menu principal: 
                     1. Listar amigos
                     2. Listar por hobby
                     3. Listar por estudio
                     4. Listar mayores de edad
                     5. Actualizar edad
                     6. Añadir hobby
                     7. Añadir estudio
                     8. Eliminar estudio
                     9. Eliminar amigo
                     10. Eliminar hobby
                     11. Insertar amigo
                     0. salir
                     Introduce la opcion: 
                    """);
            opcion = teclado.nextInt();
            switch (opcion) {
                case 1 -> amigoDAO.listarTodos();
                case 2 -> {
                    System.out.println("Introduce la hobby: ");
                    String hobby = teclado.next();
                    amigoDAO.listarPorHobby(hobby);
                }
                case 3 -> {
                    System.out.println("Introduce el nombre del estudio: ");
                    String nombre = teclado.next();
                    amigoDAO.listarPorEstudio(nombre);
                }
                case 4 -> amigoDAO.listarMayores(18);
                case 5 -> {
                    System.out.println("Introduce el nombre del amigo: ");
                    String nombre = teclado.next();
                    System.out.println("Introduce la nueva edad: ");
                    int edad = teclado.nextInt();
                    teclado.nextLine();
                    amigoDAO.actualizarEdad(nombre, edad);
                    System.out.println("Edad actualizado con exito");
                }
                case 6 -> {
                    System.out.println("Introduce el nombre del amigo: ");
                    String nombre = teclado.next();
                    System.out.println("Introduce la nueva hobby: ");
                    String hobby = teclado.nextLine();
                    amigoDAO.anadirHobby(nombre, hobby);
                    System.out.println("hobby aniadido con exito");
                }
                case 7 -> {
                    System.out.println("Introduce el nombre del Alumno: ");
                    String nombre = teclado.next();
                    System.out.println("Introduce el titulo del estudio: ");
                    String titulo = teclado.next();
                    System.out.println("Introduce el centro del estudio: ");
                    String centro = teclado.next();
                    System.out.println("Introduce el anio del estudio: ");
                    int anio = teclado.nextInt();
                    teclado.nextLine();
                    Estudio estudio = new Estudio(titulo, centro, anio);
                    amigoDAO.anadirEstudio(nombre, estudio);
                    System.out.println("Estudios anadidos con exito");
                }
                case 8 -> {
                    System.out.println("Introduce el nombre del Alumno: ");
                    String nombre = teclado.next();
                    System.out.println("Introduce el titulo del estudio: ");
                    String titulo = teclado.next();
                    amigoDAO.eliminarEstudio(nombre, titulo);
                    System.out.println("Estudios eliminados con exito");
                }
                case 9 -> {
                    System.out.println("Introduce el nombre del Alumno: ");
                    String nombre = teclado.next();
                    amigoDAO.eliminar(nombre);
                    System.out.println("Alumno eliminado exitosamente");
                }
                case 10 -> {
                    System.out.println("Introduce el nombre del Alumno: ");
                    String nombre = teclado.next();
                    System.out.println("Introduce el hobby que quieres eliminar: ");
                    String hobby = teclado.next();
                    amigoDAO.eliminarHobby(nombre, hobby);
                    System.out.println("Hobby: " + hobby + " ha sido borrado con exito");
                }
                case 11 -> {
                    System.out.println("=== INSERTAR AMIGO===");

                    System.out.print("Nombre: ");
                    String nombre = teclado.next();

                    System.out.print("Edad: ");
                    int edad = teclado.nextInt();
                    teclado.nextLine();

                    System.out.print("Número de hobbies: ");
                    int numHobbies = teclado.nextInt();
                    teclado.nextLine();

                    List<String> hobbies = new ArrayList<>();

                    for (int i = 0; i < numHobbies; i++) {
                        System.out.print("Hobby " + (i + 1) + ": ");
                        hobbies.add(teclado.nextLine());
                    }

                    System.out.print("Número de teléfonos: ");
                    int numTelefonos = teclado.nextInt();
                    teclado.nextLine();

                    List<String> telefonos = new ArrayList<>();

                    for (int i = 0; i < numTelefonos; i++) {
                        System.out.print("Teléfono " + (i + 1) + ": ");
                        telefonos.add(teclado.nextLine());
                    }

                    System.out.print("Número de estudios: ");
                    int numEstudios = teclado.nextInt();
                    teclado.nextLine();

                    List<Estudio> estudios = new ArrayList<>();

                    for (int i = 0; i < numEstudios; i++) {

                        System.out.println("Estudio " + (i + 1));

                        System.out.print("Título: ");
                        String titulo = teclado.nextLine();

                        System.out.print("Centro: ");
                        String centro = teclado.nextLine();

                        System.out.print("Año: ");
                        int anio = teclado.nextInt();
                        teclado.nextLine();

                        estudios.add(new Estudio(titulo, centro, anio));
                    }

                    Amigos amigo = new Amigos(nombre, edad, hobbies, telefonos, estudios);

                    amigoDAO.insertar(amigo);
                    System.out.println("Amigo insertado correctamente");

                }
                case 0 -> {
                    System.out.println("Adios ");
                }
                default -> System.out.println("opcion incorrecta");
            }
        }while (opcion != 0);

       }
}