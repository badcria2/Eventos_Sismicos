package main.presentacion;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import main.entidad.Usuario;
import main.logica.ArchivoUsuario;
import main.logica.Autenticador;
import main.presentacion.Menu;
import main.utilitario.LoggingConfig;

public class Login {
    private static final Logger logger = Logger.getLogger(Login.class.getName());

    private static void mostrarBienvenida() {
        //limpiarPantalla();
        System.out.println("==================================");
        System.out.println("|                                |");
        System.out.println("|       Bienvenido al Sistema    |");
        System.out.println("|                                |");
        System.out.println("==================================");
    }

    private static void mostrarLogin() {
        //limpiarPantalla();
        System.out.println("==================================");
        System.out.println("|        Inicio de Sesión        |");
        System.out.println("==================================");
    }

    private static void mostrarCargando() {
        //limpiarPantalla();
        System.out.println("==================================");
        System.out.println("|                                |");
        System.out.println("|       Cargando Aplicación...   |");
        System.out.println("|                                |");
        System.out.println("==================================");
    }



    public static void main(String[] args) {
        LoggingConfig.init();

        Scanner scanner = new Scanner(System.in);
        ArchivoUsuario archivoUsuarios = new ArchivoUsuario("C:\\Users\\reibi\\IdeaProjects\\Eventos_Sismicos\\src\\resource\\usuarios.txt");

        try {
            List<Usuario> usuarios = archivoUsuarios.leerUsuarios();
            Autenticador autenticador = new Autenticador(usuarios);

            int intentos = 0;
            boolean autenticado = false;

            mostrarBienvenida();

            while (intentos < 3 && !autenticado) {
                mostrarLogin();
                System.out.print("Ingrese nombre de usuario: ");
                String nombre = scanner.nextLine();
                System.out.print("Ingrese contraseña: ");
                String contraseña = scanner.nextLine();

                if (autenticador.autenticar(nombre, contraseña)) {
                    Usuario user = autenticador.obtenerDatosUsuario(nombre, contraseña);
                    System.out.println("Bienvenido " + user.getNombreUsuario() + "!");
                    autenticado = true;

                    // Mostrar evento de carga
                    mostrarCargando();

                    // Simular tiempo de carga
                    try {
                        Thread.sleep(2000);  // Simula un tiempo de espera de 2 segundos
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    // Redirigir al menú principal de la aplicación
                    Menu menu = new Menu(user.getNombreUsuario());
                    menu.iniciarMenu();
                } else {
                    intentos++;
                    System.out.println("Credenciales incorrectas. Intento " + intentos + " de 3.");
                }
            }

            if (!autenticado) {
                System.out.println("Ha excedido el número de intentos permitidos.");
            }

        } catch (IOException e) {
            logger.warning("Error al leer el archivo de usuarios: " + e.getMessage());
        } catch (ParseException e) {
            logger.warning("Error al iniciar el menú: " + e.getMessage());
        }

        scanner.close();
    }
}
