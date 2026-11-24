package lab11;

import java.math.BigDecimal;
import java.util.List;
import lab11.ejercicios.*;

public class PruebasAdicionales {
    private static int aprobadas;
    private static void validar(boolean b,String texto) {
        if(!b) throw new AssertionError(texto);
        aprobadas++;
        System.out.println("OK " + aprobadas + " - " + texto);
    }

    public static void main(String[] args) {
        var centro=new ObserverNotificaciones.CentroNotificaciones();
        var uno=new ObserverNotificaciones.Usuario("Uno");
        var dos=new ObserverNotificaciones.Usuario("Dos");
        centro.suscribir(uno);centro.suscribir(dos);
        centro.publicar(new ObserverNotificaciones.Notificacion("Promocion","Evento"));
        validar(uno.totalRecibidas()==1 && dos.totalRecibidas()==1,"Observer notifica a dos usuarios");
        centro.desuscribir(uno);
        centro.publicar(new ObserverNotificaciones.Notificacion("Aviso","Otro"));
        validar(uno.totalRecibidas()==1 && dos.totalRecibidas()==2,"Observer cancela suscripcion");
        var productos=List.of(new EstrategiasPromocion.Producto("A",new BigDecimal("50")),new EstrategiasPromocion.Producto("A",new BigDecimal("50")),new EstrategiasPromocion.Producto("B",new BigDecimal("20")));
        var c=new EstrategiasPromocion.CalculadoraDePrecios();
        validar(c.calcular(productos).compareTo(new BigDecimal("120"))==0,"Sin descuento");
        c.setEstrategia(new EstrategiasPromocion.DescuentoFijo());
        validar(c.calcular(productos).compareTo(new BigDecimal("108"))==0,"10 por ciento");
        c.setEstrategia(new EstrategiasPromocion.DescuentoPorcentual());
        validar(c.calcular(productos).compareTo(new BigDecimal("105"))==0,"30 por ciento a la segunda unidad igual");
        c.setEstrategia(new EstrategiasPromocion.DescuentoPorcentualAcumulado());
        validar(c.calcular(productos).compareTo(new BigDecimal("110"))==0,"50 por ciento al menor producto");
        var t=new ControlTelevisor.Televisor();
        var remoto=new ControlTelevisor.ControlRemoto();
        remoto.asignar(1,t::encender);remoto.asignar(2,t::subirVolumen);remoto.asignar(3,() -> t.cambiarCanal(9));remoto.asignar(4,t::apagar);
        remoto.presionar(1);remoto.presionar(2);remoto.presionar(3);
        validar(t.encendido() && t.volumen()==11 && t.canal()==9,"Televisor encender, volumen y canal");
        remoto.presionar(4);validar(!t.encendido(),"Televisor apagar");
        var luz=new ControlDispositivos.Dispositivo("Luz");
        var control=new ControlDispositivos.ControlRemoto();
        control.ejecutar(new ControlDispositivos.CambiarEstado(luz,true));
        validar(luz.estaEncendido(),"Encender dispositivo");
        validar(control.deshacer() && !luz.estaEncendido(),"Command deshacer");
        validar(!control.deshacer(),"Historial vacio no falla");
        BebidasPatrones.Bebida pedido=new BebidasPatrones.Canela(new BebidasPatrones.Leche(new BebidasPatrones.FabricaCafe().crear()));
        validar(pedido.precio().compareTo(new BigDecimal("9.00"))==0,"Factory Method mas Decorator");
        System.out.println("PRUEBAS ADICIONALES SUPERADAS: "+aprobadas+"/"+aprobadas);
    }
}
