package lab11.ejercicios;

import java.util.ArrayList;
import java.util.List;

public class ObserverNotificaciones {
    public interface Observador {
        void recibir(Notificacion notificacion);
    }

    public record Notificacion(String asunto, String mensaje) { }

    public static class Usuario implements Observador {
        private final String nombre;
        private final List<Notificacion> recibidas = new ArrayList<>();

        public Usuario(String nombre) { this.nombre = nombre; }

        @Override public void recibir(Notificacion notificacion) {
            recibidas.add(notificacion);
            System.out.println(nombre + " recibio [" + notificacion.asunto() + "]: " + notificacion.mensaje());
        }

        public int totalRecibidas() { return recibidas.size(); }
    }

    public static class CentroNotificaciones {
        private final List<Observador> suscritos = new ArrayList<>();

        public void suscribir(Observador usuario) {
            if(usuario != null && !suscritos.contains(usuario)) suscritos.add(usuario);
        }

        public void desuscribir(Observador usuario) { suscritos.remove(usuario); }

        public void publicar(Notificacion notificacion) {
            for(Observador observador: List.copyOf(suscritos)) observador.recibir(notificacion);
        }
    }

    public static void main(String[] args) {
        CentroNotificaciones centro = new CentroNotificaciones();
        Usuario cliente = new Usuario("Cliente");
        Usuario administrador = new Usuario("Administrador");
        centro.suscribir(cliente);
        centro.suscribir(administrador);
        centro.publicar(new Notificacion("Promocion", "Descuento de temporada"));
        centro.desuscribir(cliente);
        centro.publicar(new Notificacion("Producto", "Stock actualizado"));
        System.out.println("Cliente: " + cliente.totalRecibidas() + " | Administrador: " + administrador.totalRecibidas());
    }
}
