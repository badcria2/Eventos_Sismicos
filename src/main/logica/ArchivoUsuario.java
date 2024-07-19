package main.logica;

import main.entidad.Usuario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArchivoUsuario {
    private String rutaArchivo;

    public ArchivoUsuario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public List<Usuario> leerUsuarios() throws IOException {
        List<Usuario> usuarios = new ArrayList<>();
        BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo));
        String linea;

        while ((linea = lector.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length == 3) {
                usuarios.add(new Usuario(partes[0], partes[1], partes[2]));
            }
        }

        lector.close();
        return usuarios;
    }
}
