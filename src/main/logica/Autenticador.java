package main.logica;

import main.entidad.Usuario;

import java.util.List;

public class Autenticador {
    private List<Usuario> usuarios;

    public Autenticador(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public boolean autenticar(String nombre, String contraseña) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(nombre) && usuario.getContrasenia().equals(contraseña)) {
                return true;
            }
        }
        return false;
    }
    public Usuario obtenerDatosUsuario(String nombre, String contraseña) {
        Usuario user = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(nombre) && usuario.getContrasenia().equals(contraseña)) {
                user = usuario;
            }
        }
        return user;
    }
}