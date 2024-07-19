package main.entidad;

public class Usuario {
    private String nombre;
    private String contrasenia;

    private String nombreUsuario;

    public Usuario(String nombre, String contrasenia) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }

    public Usuario(String nombre, String contrasenia, String nombreUsuario) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.nombreUsuario = nombreUsuario;
    }


    public String getNombre() {
        return nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
