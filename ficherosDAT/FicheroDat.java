package ficherosDAT;

import java.io.*;

public class FicheroDat {
    public static void main(String[] args) {
        String fichero = "proyecto.dat";
        String[] ciudades = {"Madrid", "Murcia", "Salamanca"};
        int[] poblacion = {12265, 26153, 115675};
        int[] codPostal = {28500, 37000, 69000};

        //crear y escribir en el archivo
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(fichero))){
            for (int i = 0; i < ciudades.length; i++) {
                dos.writeUTF(ciudades[i]);
                dos.writeInt(poblacion[i]);
                dos.writeInt(codPostal[i]);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //leer
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fichero))){
        while (true){
            try {
                String ciudad = dis.readUTF();
                int pobla = dis.readInt();
                int cod = dis.readInt();
                System.out.println("Ciudad: "+ ciudad +", Poblacion: "+ pobla +", Codigo postal: "+ cod);
            }catch (EOFException e) { //end of file
            break;
            }
        }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
