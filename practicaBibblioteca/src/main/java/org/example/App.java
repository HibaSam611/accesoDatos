package org.example;

import Entidades.Ejemplar;
import Entidades.Libro;
import Entidades.Prestamo;
import Entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner teclado = new Scanner(System.in);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblio");
        EntityManager em = emf.createEntityManager();

        int opcion;
        do {
            System.out.println("Menu Biblioteca");
            System.out.println("1. Registrar libro");
            System.out.println("2. Registrar ejemplar");
            System.out.println("3. Ver stock disponible");
            System.out.println("4. Registrar usuario");
            System.out.println("5. Registrar préstamo");
            System.out.println("6. Devolver préstamo");
            System.out.println("7. Listar prestamos");
            System.out.println("8. Salir");
            System.out.println("\n Elige una opción: ");
            opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1 -> regitrarLibro(em, teclado);
                case 2 -> registrarEjemplar(em, teclado);
                case 3 -> controlarStockDisponible(em, teclado);
                case 4 -> registrarUsuarios(em, teclado);
                case 5 -> registrarPrestamo(em, teclado);
                case 6 -> devolverPrestamo(em, teclado);
                case 7 -> listarPrestamos(em, teclado);
                case 8 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción incorrecta");
            }
        } while (opcion != 8);

        em.close();
        emf.close();

    }


    public static void regitrarLibro(EntityManager em, Scanner teclado) {
        System.out.println("Introduce el nombre del libro que quieres registrar: ");
        String nombre = teclado.nextLine();
        System.out.println("Introduce el nombre del autor: ");
        String autor = teclado.nextLine();
        System.out.println("Introduce el ISBN13: ");
        String ISBN13 = teclado.nextLine();
        if (isbnNoValido(ISBN13)) return;
        //comprobar si el isbn ya existe
        Libro existente = em.find(Libro.class, ISBN13);
        if (existente != null) {
            System.out.println("El libro ya existe");
            return;
        }

        Libro libro = new Libro(ISBN13, nombre, autor);
        em.getTransaction().begin();
        em.persist(libro);
        em.getTransaction().commit();
        System.out.println("Libro registrado correctamente");
    }

    private static boolean isbnNoValido(String ISBN13) {
        //comprobar que no es nulo, que la longitud es 13, los 13 son digitos y que empieza con 979
        if (ISBN13 == null ||
                ISBN13.length() != 13 ||
                !ISBN13.matches("\\d{13}") ||
                !(ISBN13.startsWith("978") || ISBN13.startsWith("979"))) {
            System.out.println("ISBN13 no válido");
            return true;
        }
        return false;
    }


    public static void registrarEjemplar(EntityManager em, Scanner teclado) {

        System.out.println("Introduce el ID del ejemplar:");
        Integer id = Integer.parseInt(teclado.nextLine());

        // comprobar que no exista
        Ejemplar eExistente = em.find(Ejemplar.class, id);
        if (eExistente != null) {
            System.out.println("Ya existe un ejemplar con ese ID");
            return;
        }

        System.out.println("Introduce el ISBN del libro:");
        String isbn = teclado.nextLine();
        if (isbnNoValido(isbn)) return;


        Libro libro = em.find(Libro.class, isbn);
        if (libro == null) {
            System.out.println("No existe ningún libro con ese ISBN");
            return;
        }

        System.out.println("Introduce el estado (Disponible / Prestado / Dañado):");
        String estado = teclado.nextLine();

        if (!estado.equalsIgnoreCase("Disponible") &&
                !estado.equalsIgnoreCase("Prestado") &&
                !estado.equalsIgnoreCase("Dañado")) {

            System.out.println("Estado no valido");
            return;
        }
        Ejemplar ejemplar = new Ejemplar(id, libro, estado);

        em.getTransaction().begin();
        em.persist(ejemplar);
        em.getTransaction().commit();

        System.out.println("Ejemplar registrado correctamente");
    }

    public static void controlarStockDisponible(EntityManager em, Scanner teclado) {
        System.out.println("Introduce el isbn del libro:");
        String isbn = teclado.nextLine();
        if (isbnNoValido(isbn)) return;
        Libro libro = em.find(Libro.class, isbn);
        if (libro == null) {
            System.out.println("El libro no existe");
            return;
        }
        long stock = (Long) em.createQuery("SELECT COUNT(e) FROM Ejemplar e WHERE e.isbn.isbn = :isbn AND e.estado = 'Disponible'")
                .setParameter("isbn", isbn).getSingleResult();
        System.out.println("El stock disponible del libro es: " + stock);
    }

    public static void registrarUsuarios(EntityManager em, Scanner teclado) {
        System.out.println("Introduce el DNI del usuario: ");
        String dni = teclado.nextLine();
        //Usuario uExistente = em.find(Usuario.class, dni);
        //no podemos usar esta manera porque la clave primaria es id y (em.find....) busca siempre por la clave primaria
//        if (uExistente != null) {
//            System.out.println("el usuario ya existe");
//            return;
//        }

        Long existe = (Long) em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.dni = :dni")
                .setParameter("dni", dni).getSingleResult();

        if (existe > 0) {
            System.out.println("El usuario ya existe");
            return;
        }

        System.out.println("Introduce el nobre del usuario: ");
        String nombre = teclado.nextLine();

        System.out.println("Introduce el email del usuario: ");
        String email = teclado.nextLine();

        System.out.println("Introduce el password del usuario: ");
        String password = teclado.nextLine();

        System.out.println("Introduce el tipo de usuario");
        String tipo = teclado.nextLine();
        if (!tipo.equalsIgnoreCase("normal") && !tipo.equalsIgnoreCase("administrador")) {
            System.out.println("Tipo de usuario invalido");
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setTipo(tipo);

        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
        System.out.println("Usuario registrado correctamente");
    }


    public static void registrarPrestamo( EntityManager em, Scanner teclado) {
        System.out.print("Introduce el DNI usuario: ");
        String dni = teclado.nextLine();

        Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.dni = :dni", Usuario.class)
                .setParameter("dni", dni)
                .getResultStream().findFirst().orElse(null);

        if (usuario == null) {
            System.out.println("Usuario no existe");
            return;
        }

        LocalDate hoy = LocalDate.now();

        if (usuario.getPenalizacionHasta() != null && usuario.getPenalizacionHasta().isAfter(hoy)) {
            System.out.println("Usuario penalizado hasta " + usuario.getPenalizacionHasta());
            return;
        }

        Long prestamosActivos = em.createQuery("SELECT COUNT(p) FROM Prestamo p WHERE p.usuario = :u AND p.fechaDevolucion IS NULL", Long.class)
                .setParameter("u", usuario)
                .getSingleResult();

        if (prestamosActivos >= 3) {
            System.out.println("Máximo de préstamos alcanzado");
            return;
        }

        System.out.print("ID ejemplar: ");
        int idEj = Integer.parseInt(teclado.nextLine());
        Ejemplar ej = em.find(Ejemplar.class, idEj);

        if (ej == null || !ej.getEstado().equalsIgnoreCase("Disponible")) {
            System.out.println("Ejemplar no disponible");
            return;
        }

        Prestamo p = new Prestamo();
        p.setUsuario(usuario);
        p.setEjemplar(ej);
        p.setFechaInicio(hoy);

        em.getTransaction().begin();
        em.persist(p);
        ej.setEstado("Prestado");
        em.getTransaction().commit();

        System.out.println("Préstamo creado. Fecha límite: " + hoy.plusDays(15));
    }

    public static void devolverPrestamo(EntityManager em, Scanner teclado) {
        System.out.print("Introduce el id de préstamo: ");
        int id = Integer.parseInt(teclado.nextLine());

        Prestamo p = em.find(Prestamo.class, id);
        if (p == null || p.getFechaDevolucion() != null) {
            System.out.println("Prestamo inválido");
            return;
        }

        LocalDate hoy = LocalDate.now();
        LocalDate limite = p.getFechaInicio().plusDays(15);

        em.getTransaction().begin();
        p.setFechaDevolucion(hoy);
        p.getEjemplar().setEstado("Disponible");

        if (hoy.isAfter(limite)) {
            Usuario u = p.getUsuario();
            LocalDate penal = hoy.plusDays(15);

            if (u.getPenalizacionHasta() != null && u.getPenalizacionHasta().isAfter(hoy)) {
                penal = u.getPenalizacionHasta().plusDays(15);
            }

            u.setPenalizacionHasta(penal);
        }

        em.getTransaction().commit();
        System.out.println("Prestamo devuelto");
    }

    public static void listarPrestamos(EntityManager em, Scanner teclado) {
        System.out.print("Introduce el DNI del usuario: ");
        String dni = teclado.nextLine();

        List<Prestamo> prestamos = em.createQuery("SELECT p FROM Prestamo p WHERE p.usuario.dni = :dni", Prestamo.class)
                .setParameter("dni", dni)
                .getResultList();

        if (prestamos == null || prestamos.isEmpty()) {
            System.out.println("Este usuario no tiene prestamos");
        }

        prestamos.forEach(p ->
                System.out.println("Préstamo " + p.getId() + " | Ejemplar " + p.getEjemplar().getId() + " | Inicio " + p.getFechaInicio() + " | Devuelto " + p.getFechaDevolucion())
        );
    }
}
