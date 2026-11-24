package lab11;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Pruebas {
    private static int comprobaciones;

    private static void verificar(boolean condicion, String descripcion) {
        if(!condicion) {
            throw new AssertionError(descripcion);
        }
        comprobaciones++;
        System.out.println("OK - " + descripcion);
    }

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        List<String> eventos = new ArrayList<>();
        Carrito.Observador observer = eventos::add;
        controlador.carrito().suscribir(observer);
        controlador.agregar(1, "Cuaderno", "50.00");
        controlador.agregar(1, "Cuaderno", "50.00");
        controlador.agregar(2, "Lapiz", "40.00");
        verificar(controlador.carrito().productos().size() == 3, "Tres productos agregados");
        verificar(controlador.carrito().subtotal().compareTo(new BigDecimal("140.00")) == 0, "Subtotal S/ 140.00");
        verificar(eventos.size() == 3, "Observer notifica altas");

        controlador.cambiarDescuento(1);
        verificar(controlador.total().compareTo(new BigDecimal("140.00")) == 0, "Sin descuento");
        controlador.cambiarDescuento(2);
        verificar(controlador.total().compareTo(new BigDecimal("126.00")) == 0, "Descuento 10 por ciento");
        controlador.cambiarDescuento(3);
        verificar(controlador.total().compareTo(new BigDecimal("125.00")) == 0, "Segunda unidad igual con 30 por ciento");
        controlador.cambiarDescuento(4);
        verificar(controlador.total().compareTo(new BigDecimal("120.00")) == 0, "Tres productos: mitad del mas barato");

        controlador.eliminar(2);
        verificar(controlador.carrito().productos().size() == 2, "Eliminar por posicion");
        verificar(controlador.deshacer(), "Command deshace eliminacion");
        verificar(controlador.carrito().productos().size() == 3, "Producto restaurado");
        controlador.vaciar();
        verificar(controlador.carrito().productos().isEmpty(), "Vaciar carrito");
        verificar(controlador.deshacer(), "Command deshace vaciado");
        verificar(controlador.carrito().productos().size() == 3, "Contenido restaurado");

        int antes = eventos.size();
        controlador.carrito().desuscribir(observer);
        controlador.agregar(3, "Borrador", "10.00");
        verificar(eventos.size() == antes, "Observer deja de recibir eventos");
        verificar(!controlador.carrito().productos().isEmpty(), "El modelo conserva los productos");
        System.out.println("Pruebas completadas: " + comprobaciones + " / " + comprobaciones);
    }
}
