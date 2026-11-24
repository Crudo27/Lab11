package lab11;

import java.math.BigDecimal;

public class Controlador {
    private final Carrito carrito = new Carrito();
    private final Comandos.Invocador invocador = new Comandos.Invocador();
    private Descuentos.Estrategia estrategia = new Descuentos.SinDescuento();

    public Carrito carrito() {
        return carrito;
    }

    public void agregar(int codigo, String nombre, String precio) {
        invocador.ejecutar(new Comandos.Agregar(carrito, new Producto(codigo, nombre, new BigDecimal(precio))));
    }

    public void eliminar(int numero) {
        invocador.ejecutar(new Comandos.Eliminar(carrito, numero - 1));
    }

    public void vaciar() {
        invocador.ejecutar(new Comandos.Vaciar(carrito));
    }

    public void cambiarDescuento(int opcion) {
        estrategia = switch(opcion) {
            case 1 -> new Descuentos.SinDescuento();
            case 2 -> new Descuentos.DiezPorCiento();
            case 3 -> new Descuentos.Repetidos();
            case 4 -> new Descuentos.TresProductos();
            default -> throw new IllegalArgumentException("Estrategia invalida.");
        };
        carrito.notificar("Estrategia de descuento: " + estrategia.nombre());
    }

    public BigDecimal total() {
        BigDecimal total = estrategia.calcular(carrito.productos());
        carrito.notificar("Total calculado: S/ " + total);
        return total;
    }

    public boolean deshacer() {
        return invocador.deshacer();
    }

    public String estrategiaActual() {
        return estrategia.nombre();
    }
}
