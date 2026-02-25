package org.example.DAO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.conexion.ConexionMongo;
import org.example.model.Amigos;
import org.example.model.Estudio;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class AmigoDAO {
    private MongoCollection<Document> amigos;

    public AmigoDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        amigos = db.getCollection("amigos");
    }

    public void insertar(Amigos amigo) {
        // convertimos la lista de Estudio (Java) a lista de Document (Mongo)
        List<Document> estudiosDocs = new ArrayList<>();
        if (amigo.getEstudios() != null) {
            for (Estudio e : amigo.getEstudios()) {
                Document estDoc = new Document("titulo", e.getTitulo())
                        .append("centro", e.getCentro())
                        .append("anio", e.getAnio());

                estudiosDocs.add(estDoc);
            }
        }

        Document doc = new Document("nombre", amigo.getNombre())
                .append("edad", amigo.getEdad())
                .append("hobbies", amigo.getHobbies())
                .append("telefonos", amigo.getTelefonos())
                .append("estudios", estudiosDocs);
        amigos.insertOne(doc);
    }

    public void listarTodos(){
        for (Document d : amigos.find()) {
            System.out.println(d.toJson());
        }
    }
    public void listarPorHobby(String hobby){
        for (Document d : amigos.find(eq("hobbies", hobby))){
            System.out.println(d.toJson());
        }
    }

    public void listarPorEstudio(String estudio){
        for (Document d : amigos.find(eq("estudios.titulo", estudio))){
            System.out.println(d.toJson());
        }
    }
    public void listarMayores(int edad) {
        for (Document d : amigos.find(
                new Document("edad", new Document("$gt", edad)))) {
            System.out.println(d.toJson());
        }
    }

    public void actualizarEdad(String nombre, Integer edadNueva){
        Document doc = amigos.find(eq("nombre", nombre)).first();
        if (doc != null) {
            amigos.updateOne(
                    eq("nombre", nombre),
                    new Document("$set", new Document("edad", edadNueva))
            );
            //amigos.updateOne(eq("nombre", nombre), Updates.set("edad", edadNueva));
        }else {
            System.out.println("No se encontro un alumno con ese nombre");
        }
    }

    public void anadirHobby(String nombre, String hobby) {
        Document doc = amigos.find(eq("nombre", nombre)).first();
        if (doc == null) {
            System.out.println("No se encontro un alumno con ese nombre");
            return;
        }
        amigos.updateOne(
                eq("nombre", nombre),
                new Document("$push", new Document("hobbies", hobby))
        );
    }
    public void eliminar(String nombre){
        Document doc = amigos.find(eq("nombre", nombre)).first();
        if (doc != null) {
            amigos.deleteOne(eq("nombre", nombre));
        }else {
            System.out.println("No existe un alumno con ese nombre");
        }
    }
    public void eliminarHobby(String nombre, String hobby) {
        amigos.updateOne(
                eq("nombre", nombre),
                new Document("$pull", new Document("hobbies", hobby))
        );
    }
    public void anadirEstudio(String nombre, Estudio estudio) {
        Document doc = amigos.find(eq("nombre", nombre)).first();
        if (doc != null) {

        Document estudioDoc = new Document("titulo", estudio.getTitulo())
                .append("centro", estudio.getCentro())
                .append("anio", estudio.getAnio());

        amigos.updateOne(
                eq("nombre", nombre),
                new Document("$push", new Document("estudios", estudioDoc))
        );
        }else {
            System.out.println("No existe un alumno con ese nombre");
        }
    }
    public void eliminarEstudio(String nombre, String titulo) {
        amigos.updateOne(
                eq("nombre", nombre),
                new Document("$pull", new Document("estudios", new Document("titulo", titulo)))
        );
    }

}
