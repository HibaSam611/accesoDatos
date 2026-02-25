package org.example.conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {

    public static final MongoClient cliente = MongoClients.create("mongodb://localhost:27017");
    public static  MongoDatabase getDatabase (){
        return cliente.getDatabase("alumnosdb");
    }


}
