package org.example;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws JAXBException, ParserConfigurationException, IOException, SAXException {

        Scanner sc = new Scanner(System.in);
        Libreria lib = new Libreria("Las hojas", "Getafe");
        lib.getLibros().add(new Libro("Cervantes", "Quijote", "El barco de Vapor", "1234", 23.55));
        lib.getLibros().add(new Libro("Fernando de Rojas", "La celestina", "aaaa", "12345", 12.90));
        lib.getLibros().add(new Libro("Cervantes", "La gitanilla.", "Editorial", "23009", 15.90)); //Añade un nuevo libro al XML y vuelve a guardarlo.

        // Creamos el contexto jAXB
        JAXBContext jaxbContext = JAXBContext.newInstance(Libreria.class);

        // Maeshalling --> objetos Java a XML
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        System.out.println("Generando xml ...");
        File file = new File("Libreria.xml");
        jaxbMarshaller.marshal(lib, file);
        jaxbMarshaller.marshal(lib, System.out);


        //Unmarshalling XML a objetos java
//        System.out.println("Leemos el XML... ");
//        Unmarshaller um = jaxbContext.createUnmarshaller();
//        Libreria libreria = (Libreria) um.unmarshal(new File("Libreria.xml"));
//        System.out.println(libreria);

        //Crea un método que lea el XML y muestre solo los títulos de los libros.
        System.out.println("\n 2. mostrando titulos de los libros...");
        mostrarLibros(file);

        // 3. Crea un programa que pida un autor por teclado y muestre sus libros.
        System.out.println("\n 3. mostrar libros de un autor...");
        mostrarLibrosDeAutorX(file, sc);


    }
    public static void mostrarLibros(File f) throws ParserConfigurationException, IOException, SAXException {
        if (!f.exists()) {
            System.out.println("El archivo no existe");
            return;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(f);

        NodeList libros = document.getElementsByTagName("Libro");
        for (int i = 0; i < libros.getLength(); i++) {
            Node libro = libros.item(i);
            if (libro.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) libro;
                String titulo = element.getElementsByTagName("titulo").item(0).getTextContent();
                System.out.println("Titulo: " + titulo);
            }
        }
    }

    public static void mostrarLibrosDeAutorX(File f, Scanner sc) throws ParserConfigurationException, IOException, SAXException {
        if (!f.exists()) {
            System.out.println("El archivo no existe");
            return;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(f);

        System.out.println("Introduce el nombre del autor que quieres mostrar sus libros: ");
        String autor = sc.nextLine();
        NodeList libros = document.getElementsByTagName("Libro");
        boolean libroEncontrado = false;
        for (int i = 0; i < libros.getLength(); i++) {
            Node libro = libros.item(i);
            if (libro.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) libro;
                String nombreAutor = element.getElementsByTagName("autor").item(0).getTextContent();
                if (nombreAutor.equals(autor)) {
                    String titulo = element.getElementsByTagName("titulo").item(0).getTextContent();
                    System.out.println("Los libros del autor " + nombreAutor + " son: ");
                    System.out.println("Titulo: " + titulo);
                    libroEncontrado = true;

                }if (!libroEncontrado){
                    System.out.println("No hay libros para el nombre introducido.");
                }
            }
        }

    }
}


