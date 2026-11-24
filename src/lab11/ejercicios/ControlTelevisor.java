package lab11.ejercicios;

import java.util.HashMap;
import java.util.Map;

public class ControlTelevisor {
    public static class Televisor {
        private boolean encendido;
        private int volumen=10;
        private int canal=1;

        public void encender() { encendido=true; }
        public void apagar() { encendido=false; }
        public void subirVolumen() { if(encendido && volumen < 100) volumen++; }
        public void bajarVolumen() { if(encendido && volumen > 0) volumen--; }
        public void cambiarCanal(int nuevo) { if(encendido && nuevo > 0) canal=nuevo; }
        public String estado() { return "Encendido=" + encendido + " Volumen=" + volumen + " Canal=" + canal; }
        public boolean encendido() { return encendido; }
        public int volumen() { return volumen; }
        public int canal() { return canal; }
    }

    public interface Comando { void ejecutar(); }

    public static class ControlRemoto {
        private final Map<Integer,Comando> botones=new HashMap<>();
        public void asignar(int boton,Comando comando) { botones.put(boton,comando); }
        public void presionar(int boton) {
            Comando comando=botones.get(boton);
            if(comando != null) comando.ejecutar();
        }
    }

    public static void main(String[] args) {
        Televisor televisor=new Televisor();
        ControlRemoto remoto=new ControlRemoto();
        remoto.asignar(1,televisor::encender);
        remoto.asignar(2,televisor::apagar);
        remoto.asignar(3,televisor::subirVolumen);
        remoto.asignar(4,televisor::bajarVolumen);
        remoto.asignar(5,() -> televisor.cambiarCanal(7));
        for(int numero:new int[]{1,3,3,4,5,2}) {
            remoto.presionar(numero);
            System.out.println("Boton " + numero + " -> " + televisor.estado());
        }
    }
}
