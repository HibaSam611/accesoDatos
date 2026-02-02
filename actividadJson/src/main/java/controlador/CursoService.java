package controlador;

import com.google.gson.Gson;
import model.Curso;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class CursoService {
    public Curso cargarCurso() {
        Gson gson = new Gson();

        return gson.fromJson(new InputStreamReader(
                getClass().getResourceAsStream("/alumnos.json")),
                Curso.class
        );
    }
    public void guardarCurso(Curso curso) throws IOException {
        Gson gson = new Gson();
        try(FileWriter fw = new FileWriter("alumnos.json")) {
            fw.write(gson.toJson(curso));
            //gson.toJson(curso, fw)   //escribir/
        }
    }

}
