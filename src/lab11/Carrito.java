package lab11;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    public interface Observador {
        void actualizar(String evento);
    }

    public static class Usuario implements Observador {
        private final String nombre;

        public Usuario(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void actualizar(String evento) {
            System.out.println(nombre + ": " + evento);
        }
    }

    private final List<Producto> productos = new ArrayList<>();
    private final List<Observador> observadores = new ArrayList<>();

    public void suscribir(Observador observador) {
        if(observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void desuscribir(Observador observador) {
        observadores.remove(observador);
    }

    public void notificar(String mensaje) {
        for(Observador observador : List.copyOf(observadores)) {
            observador.actualizar(mensaje);
        }
    }

    public void agregar(Producto producto) {
        agregarEn(productos.size(), producto);
    }

    public void agregarEn(int posicion, Producto producto) {
        productos.add(posicion, producto);
        notificar("Producto agregado: " + producto.nombre());
    }

    public Producto eliminar(int posicion) {
        Producto producto = productos.remove(posicion);
        notificar("Producto eliminado: " + producto.nombre());
        return producto;
    }

    public void vaciar() {
        productos.clear();
        notificar("Carrito vaciado");
    }

    public List<Producto> productos() {
        return List.copyOf(productos);
    }

    public BigDecimal subtotal() {
        return productos.stream().map(Producto::precio).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
