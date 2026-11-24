package lab11;

import java.util.Scanner;

public class Vista {
    private final Controlador controlador;
    private final Scanner scanner;
    private final Carrito.Usuario cliente = new Carrito.Usuario("Cliente");
    private final Carrito.Usuario administrador = new Carrito.Usuario("Administrador");

    public Vista(Controlador controlador, Scanner scanner) {
        this.controlador = controlador;
        this.scanner = scanner;
        controlador.carrito().suscribir(cliente);
        controlador.carrito().suscribir(administrador);
    }

    private int entero(String mensaje) {
        System.out.print(mensaje);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public void mostrar() {
        int opcion = -1;
        while(opcion != 0) {
            System.out.println("\n===== CARRITO DE COMPRAS =====");
            System.out.println("1. Agregar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Vaciar carrito");
            System.out.println("4. Mostrar carrito");
            System.out.println("5. Cambiar descuento");
            System.out.println("6. Calcular total");
            System.out.println("7. Activar o desactivar notificaciones");
            System.out.println("8. Deshacer ultima operacion");
            System.out.println("0. Salir");
            try {
                opcion = entero("Opcion: ");
                switch(opcion) {
                    case 1 -> {
                        int codigo = entero("Codigo: ");
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Precio: ");
                        controlador.agregar(codigo, nombre, scanner.nextLine());
                    }
                    case 2 -> controlador.eliminar(entero("Numero de producto: "));
                    case 3 -> controlador.vaciar();
                    case 4 -> listar();
                    case 5 -> {
                        System.out.println("1. Sin descuento | 2. 10 % | 3. 30 % repetidos | 4. 50 % al mas barato");
                        controlador.cambiarDescuento(entero("Estrategia: "));
                    }
                    case 6 -> System.out.println("TOTAL: S/ " + controlador.total());
                    case 7 -> suscripciones();
                    case 8 -> System.out.println(controlador.deshacer() ? "Operacion revertida." : "Sin operaciones para deshacer.");
                    case 0 -> System.out.println("Hasta luego.");
                    default -> System.out.println("Opcion invalida.");
                }
            }
            catch(IllegalArgumentException | IndexOutOfBoundsException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void listar() {
        int numero = 1;
        for(Producto producto : controlador.carrito().productos()) {
            System.out.println(numero++ + ". " + producto.nombre() + " - S/ " + producto.precio());
        }
        System.out.println("Subtotal: S/ " + controlador.carrito().subtotal());
        System.out.println("Descuento activo: " + controlador.estrategiaActual());
    }

    private void suscripciones() {
        int usuario = entero("1. Cliente | 2. Administrador: ");
        int accion = entero("1. Suscribir | 2. Desuscribir: ");
        Carrito.Observador observador = switch(usuario) {
            case 1 -> cliente;
            case 2 -> administrador;
            default -> throw new IllegalArgumentException("Usuario invalido.");
        };
        if(accion == 1) {
            controlador.carrito().suscribir(observador);
        }
        else if(accion == 2) {
            controlador.carrito().desuscribir(observador);
        }
        else {
            throw new IllegalArgumentException("Accion invalida.");
        }
        System.out.println("Preferencia actualizada.");
    }
}
