package lab11;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Comandos {
    public interface Comando {
        void ejecutar();
        void deshacer();
    }

    public static class Agregar implements Comando {
        private final Carrito carrito;
        private final Producto producto;
        private int indice;

        public Agregar(Carrito carrito, Producto producto) {
            this.carrito = carrito;
            this.producto = producto;
        }

        @Override
        public void ejecutar() {
            indice = carrito.productos().size();
            carrito.agregar(producto);
        }

        @Override
        public void deshacer() {
            carrito.eliminar(indice);
        }
    }

    public static class Eliminar implements Comando {
        private final Carrito carrito;
        private final int indice;
        private Producto eliminado;

        public Eliminar(Carrito carrito, int indice) {
            this.carrito = carrito;
            this.indice = indice;
        }

        @Override
        public void ejecutar() {
            eliminado = carrito.eliminar(indice);
        }

        @Override
        public void deshacer() {
            carrito.agregarEn(indice, eliminado);
        }
    }

    public static class Vaciar implements Comando {
        private final Carrito carrito;
        private List<Producto> anteriores;

        public Vaciar(Carrito carrito) {
            this.carrito = carrito;
        }

        @Override
        public void ejecutar() {
            anteriores = carrito.productos();
            carrito.vaciar();
        }

        @Override
        public void deshacer() {
            for(Producto producto : anteriores) {
                carrito.agregar(producto);
            }
        }
    }

    public static class Invocador {
        private final Deque<Comando> historial = new ArrayDeque<>();

        public void ejecutar(Comando comando) {
            comando.ejecutar();
            historial.push(comando);
        }

        public boolean deshacer() {
            if(historial.isEmpty()) {
                return false;
            }
            historial.pop().deshacer();
            return true;
        }
    }
}
