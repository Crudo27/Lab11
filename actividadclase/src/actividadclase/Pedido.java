package actividadclase;

import java.util.List;
import java.util.ArrayList;

public class Pedido {
	private List<Observador> observadores = new ArrayList<>();
	private Strategy estrategia;
    private double precio;
    private String estado = "Pendiente";

    public Pedido(double precio, Strategy estrategia) {
        this.precio = precio;
        this.estrategia = estrategia;
    }

    public void suscribir(Observador observador) {
        observadores.add(observador);
    }

    public void cambiarEstado(String nuevoEstado) {
        estado = nuevoEstado;
        for (Observador observador : observadores) {
            observador.actualizar("Pedido " + estado);
        }
    }

    public double obtenerTotal() {
        return estrategia.calcularDescuento(precio);
    }

    public void cambiarEstrategia(Strategy estrategia) {
        this.estrategia = estrategia;
    }
	
}
