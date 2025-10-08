package PracticaXML;

import java.io.*;
import java.util.*;

public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fichero;
        int opcion;

        System.out.println("Introduce el nombre del fichero de datos: ");
        fichero = sc.nextLine();
        File ficheroFichero = new File(fichero);

        if (!ficheroFichero.exists()) {
            System.out.println("Fichero no encontrado");
            return;
        }


        //crear fichero dada la ruta
        do {
            System.out.println("============= MENU PRINCIPAL =============");
            System.out.println("1. Añadir usuario");
            System.out.println("2. Mostrar usuarios");
            System.out.println("3. Generar fichero de concordancias");
            System.out.println("4. Salir");
            System.out.println("===========================================");
            System.out.println("Elige una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> aniadirUsuario(sc, fichero);
                case 2 -> mostrarUsuarios(ficheroFichero);
                case 3 -> generarConcordancias(ficheroFichero, sc);
                case 4 -> System.out.println("Saliendo... ");
                default -> System.out.println("Opcion no valida");
            }
        } while (opcion != 4);
        sc.close();
    }

    public static void aniadirUsuario(Scanner sc, String ruta) {

        System.out.println("Introduce el codigo y las aficiones del usuario: ");
        String codigoYAficiones = sc.nextLine();

        String[] partes = codigoYAficiones.split(" ");
        String codUsuario = partes[0];
        String[] aficiones = Arrays.copyOfRange(partes, 1, partes.length);
        File fichero = new File(ruta);

        if (fichero.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] parte = linea.split(" ");
                    if (parte[0].equals(codUsuario)) {
                        System.out.println("El usuario ya existe");
                        return;
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.out.println("Error al leer");
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true))) {

            bw.write(codigoYAficiones);
            bw.newLine();
            System.out.println("El usuario se ha registrado");
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo");
        } catch (IOException e) {
            System.out.println("Error al escribir");
        }
    }

    private static void mostrarUsuarios(File fichero) {

        if (!fichero.exists() || fichero.length() == 0) {
            System.out.println("El archivo no existe o vacio");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void generarConcordancias(File fichero, Scanner sc) {
        if (!fichero.exists() || fichero.length() == 0) {
            System.out.println("No hay datos.");
            return;
        }

        System.out.print("Mínimo de aficiones en comun (como minimo 1): ");
        int minimo;
        try {
            minimo = Integer.parseInt(sc.nextLine().trim());
            if (minimo < 1) {
                System.out.println("Valor inválido.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido.");
            return;
        }

        List<String[]> usuarios = new ArrayList<>();

        // Leer los usuarios del fichero
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                usuarios.add(linea.split(" "));
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero.");
            return;
        }

        List<String> parejas = new ArrayList<>();

        // Recorremos todas las parejas posibles
        for (int i = 0; i < usuarios.size(); i++) {
            for (int j = i + 1; j < usuarios.size(); j++) {
                String cod1 = usuarios.get(i)[0];
                String cod2 = usuarios.get(j)[0];

                // comparar aficiones
                List<String> comunes = new ArrayList<>();
                for (int a = 1; a < usuarios.get(i).length; a++) {
                    for (int b = 1; b < usuarios.get(j).length; b++) {
                        if (usuarios.get(i)[a].equals(usuarios.get(j)[b])) {
                            comunes.add(usuarios.get(i)[a]);
                        }
                    }
                }
                // si las aficiones son mayor o igual que el minimo indicado , guardamos la pareja en comunes
                if (comunes.size() >= minimo) {
                    Collections.sort(comunes); // orden alfabetico
                    String linea = cod1 + " " + cod2 + " " + String.join(" ", comunes);
                    parejas.add(linea);
                }
            }
        }

        if (parejas.isEmpty()) {
            System.out.println("No hay parejas con tantas aficiones comunes");
            return;
        }

        // Escribir y guardar las parejas en el fichero
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("concordancias.txt"))) {
            for (String p : parejas) {
                bw.write(p);
                bw.newLine();
            }
            System.out.println("Fichero 'concordancias.txt' creado con " + parejas.size() + " parejas.");
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero de salida.");
        }

    }
}
