package org.example;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"autor", "titulo", "editorial", "isbn", "precio"})  // para la estructura del libro
public class Libro {

        private String autor;
        private String titulo;
        private String editorial;
        private String isbn;
        private double precio;

    public Libro(String autor, String titulo, String editorial, String isbn, double precio) {
        this.autor = autor;
        this.titulo = titulo;
        this.editorial = editorial;
        this.isbn = isbn;
        this.precio = precio;
    }
    public Libro() {
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String toString() {
        return "Autor: " + autor + ",Titulo: " + titulo + ",Editorial: " + editorial + ",ISBN: " + isbn + ",Precio: " + precio;
    }
}
