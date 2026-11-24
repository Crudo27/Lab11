package lab11.ejercicios;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class ControlDispositivos {
    public static class Dispositivo {
        private final String nombre;
        private boolean encendido;

        public Dispositivo(String nombre) { this.nombre=nombre; }
        public void encender() { encendido=true; System.out.println(nombre+" encendido"); }
        public void apagar() { encendido=false; System.out.println(nombre+" apagado"); }
        public boolean estaEncendido() { return encendido; }
    }

    public interface Comando {
        void ejecutar();
        void deshacer();
    }

    public static class CambiarEstado implements Comando {
        private final Dispositivo dispositivo;
        private final boolean nuevo;
        private boolean anterior;

        public CambiarEstado(Dispositivo dispositivo,boolean nuevo) {
            this.dispositivo=dispositivo;this.nuevo=nuevo;
        }

        @Override public void ejecutar() {
            anterior=dispositivo.estaEncendido();
            if(nuevo) dispositivo.encender();else dispositivo.apagar();
        }

        @Override public void deshacer() {
            if(anterior) dispositivo.encender();else dispositivo.apagar();
        }
    }

    public static class ControlRemoto {
        private final Deque<Comando> historial=new ArrayDeque<>();
        public void ejecutar(Comando comando) { comando.ejecutar();historial.push(comando); }
        public boolean deshacer() {
            if(historial.isEmpty()) return false;
            historial.pop().deshacer();
            return true;
        }
    }

    public static void main(String[] args) {
        Dispositivo luz=new Dispositivo("Luz");
        Dispositivo ventilador=new Dispositivo("Ventilador");
        Dispositivo aire=new Dispositivo("Aire acondicionado");
        Dispositivo[] equipos={luz,ventilador,aire};
        ControlRemoto control=new ControlRemoto();
        try(Scanner teclado=new Scanner(System.in)) {
            int opcion;
            do {
                System.out.print("1-3 Encender Luz/Ventilador/Aire | 4-6 Apagar | 7 Deshacer | 0 Salir: ");
                try { opcion=Integer.parseInt(teclado.nextLine()); }
                catch(NumberFormatException e) { opcion=-1; }
                if(opcion >= 1 && opcion <= 6) control.ejecutar(new CambiarEstado(equipos[(opcion-1)%3],opcion<=3));
                else if(opcion==7 && !control.deshacer()) System.out.println("Sin comandos por deshacer");
                else if(opcion<0 || opcion>7) System.out.println("Opcion invalida");
            } while(opcion!=0);
        }
    }
}
